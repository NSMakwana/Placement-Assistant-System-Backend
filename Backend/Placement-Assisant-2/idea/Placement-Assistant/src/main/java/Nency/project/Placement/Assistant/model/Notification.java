package Nency.project.Placement.Assistant.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;
    private String title;
    private String message;
    private String pollId;
    private String studentId; // optional: null for all
    private boolean read;
    private LocalDateTime createdAt = LocalDateTime.now();
}
