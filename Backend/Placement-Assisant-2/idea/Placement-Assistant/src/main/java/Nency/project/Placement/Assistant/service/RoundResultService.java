package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.Notification;
import Nency.project.Placement.Assistant.model.RoundResult;
import Nency.project.Placement.Assistant.repository.RoundResultRepository;
import Nency.project.Placement.Assistant.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

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

    // Page 3 → Submit Result
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

                sendNotification(
                        input,
                        "Placement Result",
                        "Congratulations! You are placed in " +
                                input.getCompanyId() +
                                " as " + input.getDesignation()
                );

            } else {
                input.setPlacementStatus("Not Placed");

                studentRepo.findById(input.getStudentId()).ifPresent(s -> {
                    s.setPlacement_status("Not Placed");
                    studentRepo.save(s);
                });

                sendNotification(
                        input,
                        "Final Round Result",
                        "You were not selected in the final round for " +
                                input.getCompanyId()
                );
            }

        } else {
            input.setPlacementStatus("Pending");

            sendNotification(
                    input,
                    "Round Result: " + input.getRoundName(),
                    "Status: " + input.getStatus() +
                            (input.getMarks() != null ? "\nMarks: " + input.getMarks() : "")
            );
        }

        input.setSubmittedAt(Instant.now());
        return resultRepo.save(input);
    }

    // View Result Summary
    public List<RoundResult> getResultsByCompanyAndRound(
            String companyId,
            String designation,
            String roundName
    ) {
        return resultRepo.findByCompanyIdAndDesignationAndRoundName(
                companyId, designation, roundName
        );
    }

    public List<RoundResult> getResultsByStudent(String studentId) {
        return resultRepo.findByStudentId(studentId);
    }

    private void sendNotification(RoundResult input, String title, String message) {
        Notification n = new Notification();
        n.setTitle(title);
        n.setMessage(message);
        n.setStudentId(input.getStudentId());
        n.setRead(false);
        notificationService.createNotification(n);
    }
}
