package Nency.project.Placement.Assistant.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "rounds")
@Data
public class Round {
    @Id
    private String id;

    private String companyId;       // reference to Company.id
    private String designation;     // designation name (as in Company.Designation.designation)
    private String batch;           // e.g. "2023-2024"
    private String roundName;       // editable round name
    private boolean isFinalRound = false;
    private int sequence = 0;       // optional ordering
    private Instant createdAt = Instant.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public boolean isFinalRound() {
        return isFinalRound;
    }

    public void setFinalRound(boolean finalRound) {
        isFinalRound = finalRound;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
