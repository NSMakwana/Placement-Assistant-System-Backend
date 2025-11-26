package Nency.project.Placement.Assistant.repository;

import Nency.project.Placement.Assistant.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByCreatedAtAsc(
            String senderId1, String receiverId1, String senderId2, String receiverId2
    );

    List<ChatMessage> findBySenderIdOrReceiverIdOrderByCreatedAtDesc(
            String senderId,
            String receiverId
    );
}
