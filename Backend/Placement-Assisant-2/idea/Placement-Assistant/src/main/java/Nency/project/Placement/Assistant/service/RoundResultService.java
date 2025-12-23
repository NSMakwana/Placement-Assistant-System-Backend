package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.*;
import Nency.project.Placement.Assistant.repository.RoundResultRepository;
import Nency.project.Placement.Assistant.repository.RoundRepository;
import Nency.project.Placement.Assistant.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoundResultService {

    private final RoundResultRepository resultRepo;
    private final RoundRepository roundRepo;
    private final StudentRepository studentRepo;
    private final NotificationService notificationService;


    public RoundResultService(RoundResultRepository resultRepo,
                              RoundRepository roundRepo,
                              StudentRepository studentRepo,
                              NotificationService notificationService
                              ) {
        this.resultRepo = resultRepo;
        this.roundRepo = roundRepo;
        this.studentRepo = studentRepo;
        this.notificationService = notificationService;

    }

    // Stage 1: save selection (bulk) — mark students as "Cleared" for this round
    public List<RoundResult> saveSelection(String companyId, String designation, String batch, String roundId, String roundName, List<String> studentIds) {
        Optional<Round> roundOpt = roundRepo.findById(roundId);
        boolean isFinal = roundOpt.map(Round::isFinalRound).orElse(false);

        List<RoundResult> saved = new ArrayList<>();
        for (String sid : studentIds) {
            Student s = studentRepo.findById(sid).orElse(null);
            String sName = s != null ? s.getName() : null;
            String sEmail = s != null ? s.getEmail() : null;

            RoundResult r = new RoundResult();
            r.setCompanyId(companyId);
            r.setDesignation(designation);
            r.setBatch(batch);
            r.setRoundId(roundId);
            r.setRoundName(roundName);
            r.setFinalRound(isFinal);
            r.setStudentId(sid);
            r.setStudentName(sName);
            r.setStudentEmail(sEmail);
            r.setStatus("Cleared");
            r.setPlacementStatus("Pending");
            r.setSubmittedAt(Instant.now());

            saved.add(resultRepo.save(r));
        }
        return saved;
    }

    // Stage 2: submit single student result (or call in loop for bulk)
    public RoundResult submitResult(RoundResult input) {
        // Validate round
        Optional<Round> roundOpt = roundRepo.findById(input.getRoundId());
        boolean isFinal = roundOpt.map(Round::isFinalRound).orElse(input.isFinalRound());
        input.setFinalRound(isFinal);

        // decide placement status
        if (isFinal) {
            if ("Cleared".equalsIgnoreCase(input.getStatus())) {
                input.setPlacementStatus("Placed");
                // update student model
                studentRepo.findById(input.getStudentId()).ifPresent(s -> {
                    s.setPlacement_status("Placed");
                    s.setPlacedCompanyId(input.getCompanyId());
                    s.setPlacedDesignation(input.getDesignation());
                    studentRepo.save(s);
                });

                // notification
                Notification n = new Notification();
                n.setTitle("Placement: " + input.getRoundName());
                n.setMessage("Congratulations! You are placed in " + getCompanyName(input.getCompanyId()) + " as " + input.getDesignation());
                n.setStudentId(input.getStudentId());
                n.setRead(false);
                notificationService.createNotification(n);

                // email
                String subject = "Congratulations — Placed at " + getCompanyName(input.getCompanyId());
                String body = String.format("Dear %s,\n\nYou have been placed in %s for %s.\nMarks: %s\nRemarks: %s\n\nRegards",
                        input.getStudentName(), getCompanyName(input.getCompanyId()), input.getDesignation(),
                        input.getMarks(), input.getRemarks());

            } else {
                input.setPlacementStatus("Not Placed");
                // update student
                studentRepo.findById(input.getStudentId()).ifPresent(s -> {
                    s.setPlacement_status("Not Placed");
                    studentRepo.save(s);
                });

                Notification n = new Notification();
                n.setTitle("Final Round Result: " + input.getRoundName());
                n.setMessage("You were not selected in the final round for " + getCompanyName(input.getCompanyId()));
                n.setStudentId(input.getStudentId());
                n.setRead(false);
                notificationService.createNotification(n);

                String subject = "Final Round Result - " + getCompanyName(input.getCompanyId());
                String body = String.format("Dear %s,\n\nYou were not selected in the final round for %s (%s).\nRemarks: %s\n\nRegards",
                        input.getStudentName(), getCompanyName(input.getCompanyId()), input.getDesignation(), input.getRemarks());

            }
        } else {
            input.setPlacementStatus("Pending");
            Notification n = new Notification();
            n.setTitle("Round Result: " + input.getRoundName());
            n.setMessage("Your result for " + input.getRoundName() + " is: " + input.getStatus() +
                    (input.getMarks()!=null ? ("\nMarks: " + input.getMarks()) : "") +
                    (input.getRemarks()!=null ? ("\nRemarks: " + input.getRemarks()) : ""));
            n.setStudentId(input.getStudentId());
            n.setRead(false);
            notificationService.createNotification(n);

            String subject = "Round Result - " + input.getRoundName();
            String body = String.format("Dear %s,\n\nYour result for %s is: %s\nMarks: %s\nRemarks: %s\n\nRegards",
                    input.getStudentName(), input.getRoundName(), input.getStatus(), input.getMarks(), input.getRemarks());
            // optional: send email for non-final rounds

        }

        input.setSubmittedAt(Instant.now());
        return resultRepo.save(input);
    }

    public List<RoundResult> getResultsForRound(String roundId) {
        return resultRepo.findByRoundId(roundId);
    }

    public void deleteByRoundId(String roundId) {
        resultRepo.deleteByRoundId(roundId);
    }

    private String getCompanyName(String companyId) {
        // implement lookup with CompanyRepository if you want name
        // for now return companyId as fallback
        return companyId;
    }
}
