package Nency.project.Placement.Assistant.Controller;
import Nency.project.Placement.Assistant.model.Admin;
import Nency.project.Placement.Assistant.model.Student;
import Nency.project.Placement.Assistant.model.User;
import Nency.project.Placement.Assistant.payload.LoginRequest;
import Nency.project.Placement.Assistant.payload.SignupRequest;
import Nency.project.Placement.Assistant.payload.UserResponse;
import Nency.project.Placement.Assistant.repository.AdminRepository;
import Nency.project.Placement.Assistant.repository.StudentRepository;
import Nency.project.Placement.Assistant.repository.UserRepository;
import Nency.project.Placement.Assistant.service.StudentService;
import Nency.project.Placement.Assistant.service.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3002","https://placement-assistant-system.vercel.app"},allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/user")

public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final UserServices userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    public UserController(UserServices userService) {
        this.userService = userService;
    }
    @Autowired
    private AdminRepository adminRepository;


    //    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
@GetMapping
public List<User> getAllUsers() {
    logger.info("Fetching all students...");
    List<User> users = userService.getAllUsers();
//        List<Student> students = studentRepository.findAll();
    if (users.isEmpty()) {
        logger.warn("No user found in the database.");
    } else {
        logger.info("Found {} users.", users.size());
    }
    return users;
}

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        User newUser = new User();
        newUser.setName(signupRequest.getName());
        newUser.setEno(signupRequest.getEno());
        newUser.setEmail(signupRequest.getEmail());
        // Store the password as plain text (not recommended for production)
        newUser.setPassword(signupRequest.getPassword());

        // Assign role based on email and password criteria for admin, otherwise student
        if (signupRequest.getEmail().equalsIgnoreCase("nencysmakwana@gmail.com")
                && signupRequest.getPassword().equals("nency@rollwala")) {
            newUser.setRole("admin");
        } else {
            newUser.setRole("student");
        }

        // hasSubmittedAgreement remains false by default
        userRepository.save(newUser);
        return ResponseEntity.ok("Signup successful! Please log in.");
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        // Simple lookup of the user
//        User user = userRepository.findByEmail(loginRequest.getEmail());
//        // Compare plain text passwords directly
//        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
//            Map<String, Object> response = new HashMap<>();
//            response.put("user", new User(
//                    user.getEmail(),
//                    user.getRole(),
//                    user.getName(),
//                    user.getEno(),
//                    user.isHasSubmittedAgreement()
//            ));
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(401).body("Invalid email or password");
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // 1. Check Admin collection first
        Admin admin = adminRepository.findByEmail(loginRequest.getEmail());
        if (admin != null && admin.getPassword().equals(loginRequest.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("user", Map.of(
                    "name", admin.getName(),
                    "email", admin.getEmail(),
                    "role", admin.getRole() // superadmin | admin | subadmin
            ));
            return ResponseEntity.ok(response);
        }

        // 2. If not admin, check Student(User) collection
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("user", Map.of(
                    "name", user.getName(),
                    "email", user.getEmail(),
                    "eno", user.getEno(),
                    "role", "student",
                    "hasSubmittedAgreement", user.isHasSubmittedAgreement()
            ));
            return ResponseEntity.ok(response);
        }

        // 3. If not found in both
        return ResponseEntity.status(401).body("Invalid email or password");
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
    @PostMapping("/upload_user")
    public ResponseEntity<?> createUsersInBulk(@RequestBody List<User> users) {
        try {
            List<User> savedUsers = userRepository.saveAll(users);
            return ResponseEntity.ok(savedUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving users: " + e.getMessage());
        }
    }

}
