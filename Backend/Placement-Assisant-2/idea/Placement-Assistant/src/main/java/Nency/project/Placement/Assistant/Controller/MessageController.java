package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.Message;
import Nency.project.Placement.Assistant.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = {"http://localhost:3000", "https://placement-assistant-system.vercel.app"})
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Send a message
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        message.setTimestamp(LocalDateTime.now());
        message.setRead(false);
        Message saved = messageRepository.save(message);
        return ResponseEntity.ok(saved);
    }

    // Get all messages between two users
    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getConversation(
            @RequestParam String user1, @RequestParam String user2) {
        List<Message> messages = messageRepository.findBySenderIdAndReceiverId(user1, user2);
        messages.addAll(messageRepository.findBySenderIdAndReceiverId(user2, user1));
        messages.sort((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));
        return ResponseEntity.ok(messages);
    }

    // Mark message as read
    @PutMapping("/read/{messageId}")
    public ResponseEntity<Message> markAsRead(@PathVariable String messageId) {
        Message message = messageRepository.findById(messageId).orElse(null);
        if (message != null) {
            message.setRead(true);
            messageRepository.save(message);
        }
        return ResponseEntity.ok(message);
    }
}
