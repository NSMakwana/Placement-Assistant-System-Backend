package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.service.GeminiExtractionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:3000","https://placement-assistant-system.vercel.app"})
@RestController
@RequestMapping("/api/jd")
public class JDExtractionController {

    private final GeminiExtractionService geminiExtractionService;

    public JDExtractionController(GeminiExtractionService geminiExtractionService) {
        this.geminiExtractionService = geminiExtractionService;
    }

    @PostMapping("/extract-jd")
    public ResponseEntity<?> parseJD(@RequestParam("file") MultipartFile file) {
        try {
            // Extract text from PDF
            // **Important:** You still need to extract the text from the PDF before sending it to the Hugging Face model.
            String jdText = extractTextFromPDF(file);

            // Call the service method to get the JSON string response from Hugging Face
            String hfResponse = geminiExtractionService.extractCompanyDetailsFromJD(jdText);

            // Return the JSON string to the frontend
            return ResponseEntity.ok(hfResponse);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error parsing file: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error extracting data: " + e.getMessage());
        }
    }

    private String extractTextFromPDF(MultipartFile file) throws IOException {
        org.apache.pdfbox.pdmodel.PDDocument document = null;
        try {
            document = org.apache.pdfbox.pdmodel.PDDocument.load(file.getInputStream());
            org.apache.pdfbox.text.PDFTextStripper stripper = new org.apache.pdfbox.text.PDFTextStripper();
            return stripper.getText(document);
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }
}