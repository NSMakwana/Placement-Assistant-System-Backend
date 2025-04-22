package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.service.GeminiExtractionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = {"http://localhost:3000","https://placement-assistant-system.vercel.app"})
@RestController
@RequestMapping("/api/jd")
public class JDExtractionController {

    private final GeminiExtractionService geminiExtractionService;

    // Inject the service
    public JDExtractionController(GeminiExtractionService geminiExtractionService) {
        this.geminiExtractionService = geminiExtractionService;
    }

    // POST endpoint to extract details from job description
    @PostMapping("/extract-jd")
    public ResponseEntity<?> parseJD(@RequestParam("file") MultipartFile file) {
        try {
            // Extract text from PDF
            String jdText = extractTextFromPDF(file); // Replace with your actual PDF extraction logic

            // Call the service method to extract company details from job description
            String hfResponse = geminiExtractionService.extractCompanyDetailsFromJD(jdText);

            // Return the response back to the frontend
            return ResponseEntity.ok(hfResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error parsing file: " + e.getMessage());
        }
    }

    // Method to extract text from PDF file (you can replace this with your actual PDF extraction logic)
    private String extractTextFromPDF(MultipartFile file) {
        // For now, this is just a placeholder. Implement your PDF extraction logic here.
        return "Sample job description text extracted from PDF file.";
    }
}
