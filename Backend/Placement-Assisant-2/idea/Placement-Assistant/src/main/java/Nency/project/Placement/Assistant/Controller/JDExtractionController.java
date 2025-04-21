package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.Company;
import Nency.project.Placement.Assistant.service.GeminiExtractionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = {"http://localhost:3000", "https://placement-assistant-system.vercel.app"})
@RestController
@RequestMapping("/api/jd")
public class JDExtractionController {

    private final GeminiExtractionService geminiService;

    public JDExtractionController(GeminiExtractionService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/extract-jd")
    public ResponseEntity<Company> extractCompanyDetails(@RequestParam("file") MultipartFile file) {
        try {
            Company companyDetails = geminiService.extractCompanyDetailsFromJD(file);
            return ResponseEntity.ok(companyDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/test-gemini")
    public ResponseEntity<String> testGemini() {
        try {
            String result = geminiService.testSimplePrompt(); // Custom method we'll add below
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gemini API test failed");
        }
    }

}
