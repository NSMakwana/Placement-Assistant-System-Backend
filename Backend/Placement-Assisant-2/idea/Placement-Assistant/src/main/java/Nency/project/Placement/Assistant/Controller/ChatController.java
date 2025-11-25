package Nency.project.Placement.Assistant.Controller;



import Nency.project.Placement.Assistant.model.ChatMessage;
import Nency.project.Placement.Assistant.model.User;
import Nency.project.Placement.Assistant.repository.UserRepository;
import Nency.project.Placement.Assistant.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private ChatService chatService;


    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

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
