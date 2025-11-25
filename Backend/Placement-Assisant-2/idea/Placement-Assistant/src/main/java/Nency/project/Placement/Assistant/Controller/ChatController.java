package Nency.project.Placement.Assistant.Controller;



import Nency.project.Placement.Assistant.model.ChatMessage;
import Nency.project.Placement.Assistant.model.User;
import Nency.project.Placement.Assistant.repository.UserRepository;
import Nency.project.Placement.Assistant.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final UserRepository userRepository;
    private final ChatService chatService;

    // ✔ Get all users for chat
    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // ✔ Get chat between 2 users
    @GetMapping("/get/{senderId}/{receiverId}")
    public List<ChatMessage> getMessages(
            @PathVariable String senderId,
            @PathVariable String receiverId
    ) {
        return chatService.getMessages(senderId, receiverId);
    }

    // ✔ Send new message
    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestBody ChatMessage message) {
        return chatService.sendMessage(message);
    }
}
