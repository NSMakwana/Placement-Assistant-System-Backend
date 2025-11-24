package Nency.project.Placement.Assistant.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "polls")
public class Poll {

    @Id
    private String id;

    private String companyId;
    private String companyName;
    private String question;
    private List<String> options;
    private String batch;

    private boolean isActive = true; // default active
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public Poll() {}

    public Poll(String companyId, String companyName, String question, List<String> options, String batch) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.question = question;
        this.options = options;
        this.batch = batch;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }

    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }

    public boolean isActive() { return isActive; } // important for JSON
    public void setActive(boolean active) { this.isActive = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
