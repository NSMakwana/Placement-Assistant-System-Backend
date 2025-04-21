package Nency.project.Placement.Assistant.Controller;

//package Nency.project.Placement.Assistant.Controller;
//
//import Nency.project.Placement.Assistant.model.Company;
//import Nency.project.Placement.Assistant.service.GeminiExtractionService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@CrossOrigin(origins = {"http://localhost:3000", "https://placement-assistant-system.vercel.app"})
//@RestController
//@RequestMapping("/api/jd")
//public class JDExtractionController {
//
//    private final GeminiExtractionService geminiService;
//
//    public JDExtractionController(GeminiExtractionService geminiService) {
//        this.geminiService = geminiService;
//    }
//
//    @PostMapping("/extract-jd")
//    public ResponseEntity<Company> extractCompanyDetails(@RequestParam("file") MultipartFile file) {
//        try {
//            Company companyDetails = geminiService.extractCompanyDetailsFromJD(file);
//            return ResponseEntity.ok(companyDetails);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    @GetMapping("/test-gemini")
//    public ResponseEntity<String> testGemini() {
//        try {
//            String result = geminiService.testSimplePrompt(); // Custom method we'll add below
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gemini API test failed");
//        }
//    }
//
//}



import Nency.project.Placement.Assistant.service.GeminiExtractionService;
//import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000", "https://placement-assistant-system.vercel.app"})
@RestController
@RequestMapping("/api/jd")
public class JDExtractionController {

    @Autowired
    private GeminiExtractionService geminiService;

    @PostMapping("/extract-jd")
    public ResponseEntity<Map<String, Object>> extractJobDetails(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, Object> extractedData = geminiService.extractDataFromPdf(file);
            return ResponseEntity.ok(extractedData); // Use ResponseEntity.ok() for 200 OK
        } catch (IOException e) {
            System.err.print("Error processing PDF file: " + e.getMessage());
            return ResponseEntity.internalServerError().build(); // Use ResponseEntity.internalServerError().build() for 500
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); // Use ResponseEntity.badRequest().body() for 400 with error message
        }
    }
}