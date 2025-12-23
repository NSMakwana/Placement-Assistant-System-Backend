package Nency.project.Placement.Assistant.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "round_results")
@Data
public class RoundResult {

    @Id
    private String id;

    private String companyId;
    private String designation;
    private String batch;

    private String roundId;       // reference to Round.id
    private String roundName;     // snapshot/ editable at submission
    private boolean finalRound;   // snapshot of round.isFinalRound at submit time

    private String studentId;
    private String studentName;
    private String studentEmail;

    private String status;        // "Cleared" or "Not Cleared"
    private Double marks;
    private String remarks;

    private String placementStatus; // "Placed" | "Not Placed" | "Pending"
    private Instant submittedAt = Instant.now();
}
