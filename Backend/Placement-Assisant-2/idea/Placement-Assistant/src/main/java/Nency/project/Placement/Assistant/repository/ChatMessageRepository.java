package Nency.project.Placement.Assistant.repository;



import Nency.project.Placement.Assistant.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
            String senderId, String receiverId,
            String receiverId2, String senderId2
    );
}

