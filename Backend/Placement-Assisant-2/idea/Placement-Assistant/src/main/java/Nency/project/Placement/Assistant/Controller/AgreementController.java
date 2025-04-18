package Nency.project.Placement.Assistant.Controller;
import Nency.project.Placement.Assistant.repository.UserRepository;
import Nency.project.Placement.Assistant.model.Agreement;
import Nency.project.Placement.Assistant.model.User;
import Nency.project.Placement.Assistant.repository.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3001","http://localhost:3000","https://placement-assistant-system.vercel.app"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/agreements")
public class AgreementController {

    @Autowired
    private AgreementRepository agreementRepository;

    private static final String UPLOAD_DIR = "uploads/";

    // In AgreementController or a dedicated UserController


    @PostMapping
    public ResponseEntity<?> submitAgreement(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("course") String course,
            @RequestParam("semester") String semester,
            @RequestParam("confirm") String confirm,
            @RequestParam("agree") String agree,
            @RequestParam("file") MultipartFile file) {



        // Save file to server
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            // Save agreement details to the database
            Agreement agreement = new Agreement();
            agreement.setName(name);
            agreement.setEmail(email);
            agreement.setPhone(phone);
            agreement.setCourse(course);
            agreement.setSemester(semester);
            agreement.setConfirm(confirm);
            agreement.setAgree(agree);
            agreement.setFilePath(filePath.toString());

            agreementRepository.save(agreement);
            return ResponseEntity.ok("Agreement submitted successfully!");

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving file: " + e.getMessage());
        }
    }
}