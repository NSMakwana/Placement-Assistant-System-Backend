package Nency.project.Placement.Assistant.controller;

import Nency.project.Placement.Assistant.model.ChatMessage;
import Nency.project.Placement.Assistant.model.User;
import Nency.project.Placement.Assistant.repository.UserRepository;
import Nency.project.Placement.Assistant.service.ChatService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;

    public ChatController(ChatService chatService, UserRepository userRepository) {
        this.chatService = chatService;
        this.userRepository = userRepository;
    }

    // ================= LIST ALL USERS =================
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(required = false) String excludeId) {
        List<User> users = userRepository.findAll();

        List<Map<String, Object>> result = users.stream()
                .filter(u -> excludeId == null || !u.getId().equals(excludeId))
                .map(u -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", u.getId());
                    m.put("name", u.getName());
                    m.put("email", u.getEmail());
                    m.put("role", u.getRole());
                    m.put("eno", u.getEno());
                    return m;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // ================= SEND MESSAGE =================
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessage msg) {

        if (msg.getSenderId() == null || msg.getReceiverId() == null || msg.getMessage() == null) {
            return ResponseEntity.badRequest().body("Missing required fields.");
        }

        ChatMessage saved = chatService.save(msg);
        return ResponseEntity.ok(saved);
    }

    // ================= GET CONVERSATION =================
    @GetMapping("/get/{user1}/{user2}")
    public ResponseEntity<?> getConversation(@PathVariable String user1, @PathVariable String user2) {
        return ResponseEntity.ok(chatService.getConversation(user1, user2));
    }

    // ================= USER'S ALL CHATS =================
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserChats(@PathVariable String userId) {
        return ResponseEntity.ok(chatService.getUserMessages(userId));
    }
}
