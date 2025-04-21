package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.Company;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Service
public class GeminiExtractionService {

    @Value("${GEMINI_API_KEY}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Company extractCompanyDetailsFromJD(MultipartFile file) throws IOException, InterruptedException {
        String pdfText = extractTextFromPDF(file);
        String prompt = "Extract company placement details from the following text and convert to JSON:\n" + pdfText;

        String jsonResponse = callGeminiAPI(prompt);

        return objectMapper.readValue(jsonResponse, Company.class);
    }

    private String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    
    }

    private String callGeminiAPI(String prompt) throws IOException, InterruptedException {
        String endpoint = "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent?key=" + apiKey;

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt))))
        );

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode root = objectMapper.readTree(response.body());
        return root.get("candidates").get(0).get("content").get("parts").get(0).get("text").asText();
    }
    public String testSimplePrompt() throws IOException,InterruptedException{

        String endpoint = "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent?key=" + apiKey;


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                {
                  "contents": [
                    {
                      "parts": [
                        { "text": "Say hello from Gemini!" }
                      ]
                    }
                  ]
                }
            """))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.err.println("Gemini API Error Response: " + response.body());
            return response.body();

        } else {
            throw new IOException("Gemini API error: " + response.statusCode() + " - " + response.body());
        }
    }

}
