package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.*;
import Nency.project.Placement.Assistant.repository.RoundRepository;
import Nency.project.Placement.Assistant.repository.RoundResultRepository;
import Nency.project.Placement.Assistant.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoundResultService {

    private final RoundResultRepository resultRepo;
    private final StudentRepository studentRepo;
    private final NotificationService notificationService;

    public RoundResultService(RoundResultRepository resultRepo,
                              StudentRepository studentRepo,
                              NotificationService notificationService) {
        this.resultRepo = resultRepo;
        this.studentRepo = studentRepo;
        this.notificationService = notificationService;
    }

    // Stage 1: Selection
    public List<RoundResult> saveSelection(
            String companyId,
            String designation,
            String batch,
            String roundNo,
            String roundName,
            List<String> studentIds,
            boolean isFinalRound
    ) {
        List<RoundResult> saved = new ArrayList<>();

        for (String sid : studentIds) {
            Student s = studentRepo.findById(sid).orElse(null);

            RoundResult r = new RoundResult();
            r.setCompanyId(companyId);
            r.setDesignation(designation);
            r.setBatch(batch);
            r.setRoundNo(roundNo);
            r.setRoundName(roundName);
            r.setFinalRound(isFinalRound);
            r.setStudentId(sid);
            r.setStudentName(s != null ? s.getName() : null);
            r.setStudentEmail(s != null ? s.getEmail() : null);
            r.setStatus("Cleared");
            r.setPlacementStatus("Pending");
            r.setSubmittedAt(Instant.now());

            saved.add(resultRepo.save(r));
        }
        return saved;
    }

    // Stage 2: Submit result
    public RoundResult submitResult(RoundResult input) {

        if (input.isFinalRound()) {
            if ("Cleared".equalsIgnoreCase(input.getStatus())) {
                input.setPlacementStatus("Placed");

                studentRepo.findById(input.getStudentId()).ifPresent(s -> {
                    s.setPlacement_status("Placed");
                    s.setPlacedCompanyId(input.getCompanyId());
                    s.setPlacedDesignation(input.getDesignation());
                    studentRepo.save(s);
                });

                Notification n = new Notification();
                n.setTitle("Placement Result");
                n.setMessage("Congratulations! You are placed for " + input.getDesignation());
                n.setStudentId(input.getStudentId());
                n.setRead(false);
                notificationService.createNotification(n);

            } else {
                input.setPlacementStatus("Not Placed");
            }
        } else {
            input.setPlacementStatus("Pending");
        }

        input.setSubmittedAt(Instant.now());
        return resultRepo.save(input);
    }

    public List<RoundResult> getResultsForRound(
            String companyId,
            String designation,
            String roundNo
    ) {
        return resultRepo.findByCompanyIdAndDesignationAndRoundNo(
                companyId, designation, roundNo
        );
    }
}
