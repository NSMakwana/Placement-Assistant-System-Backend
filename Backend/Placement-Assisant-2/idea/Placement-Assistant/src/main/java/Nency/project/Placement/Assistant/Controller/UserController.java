package Nency.project.Placement.Assistant.Controller;
import Nency.project.Placement.Assistant.model.User;
import Nency.project.Placement.Assistant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3001", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/user")

public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }
        userRepository.save(user);
        return ResponseEntity.ok("Signup successful!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials!");
        }
        return ResponseEntity.ok("Login successful!");
    }
    @GetMapping("/hasSubmitted")
    public ResponseEntity<Boolean> checkIfSubmitted(@RequestParam String email) {
        // Directly get the user from the repository based on the email
        User user = userRepository.findByEmail(email);

        // Check if the user is found and return the hasSubmitted status
        if (user != null) {
            return ResponseEntity.ok(user.getHasSubmittedAgreement());
        }

        // If user not found, return NOT_FOUND response with false
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
    @PostMapping("/updateHasSubmitted")
    public ResponseEntity<?> updateHasSubmitted(@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setHasSubmittedAgreement(true);
            userRepository.save(user);
            return ResponseEntity.ok("Submission status updated!");
        }
        return ResponseEntity.status(404).body("User not found");
    }
}
