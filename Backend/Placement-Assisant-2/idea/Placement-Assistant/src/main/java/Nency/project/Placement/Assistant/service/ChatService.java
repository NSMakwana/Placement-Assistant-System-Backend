package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.ChatMessage;
import Nency.project.Placement.Assistant.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatMessageRepository repo;

    public ChatService(ChatMessageRepository repo) {
        this.repo = repo;
    }

    public ChatMessage save(ChatMessage msg) {
        return repo.save(msg);
    }

    public List<ChatMessage> getConversation(String u1, String u2) {
        return repo.findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByCreatedAtAsc(
                u1, u2, u2, u1
        );
    }

    public List<ChatMessage> getUserMessages(String userId) {
        return repo.findBySenderIdOrReceiverIdOrderByCreatedAtDesc(userId, userId);
    }

    public void deleteMessage(String messageId) {
        repo.deleteById(messageId);
    }
}
