package Nency.project.Placement.Assistant.service;



import Nency.project.Placement.Assistant.model.ChatMessage;
import Nency.project.Placement.Assistant.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage sendMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getMessages(String senderId, String receiverId) {
        return chatMessageRepository
                .findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
                        senderId, receiverId,
                        senderId, receiverId
                );
    }
}
