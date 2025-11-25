package Nency.project.Placement.Assistant.model;



import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "chat_messages")
public class ChatMessage {

    @Id
    private String id;

    private String senderId;
    private String receiverId;
    private String message;
    private String senderRole;

    private Date timestamp = new Date();
}
