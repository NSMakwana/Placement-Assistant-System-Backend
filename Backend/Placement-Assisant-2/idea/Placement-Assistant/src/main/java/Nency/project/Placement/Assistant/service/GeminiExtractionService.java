//package Nency.project.Placement.Assistant.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import com.google.cloud.vertexai.VertexAI;
//import com.google.cloud.vertexai.api.Content;
//import com.google.cloud.vertexai.api.Part;
//import com.google.cloud.vertexai.api.Blob;
//import com.google.cloud.vertexai.api.GenerateContentResponse;
//import com.google.cloud.vertexai.api.GenerationConfig;
//import com.google.cloud.vertexai.generativeai.GenerativeModel;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.*;
//
//@Service
//public class GeminiExtractionService {
//
//    @Value("${google.cloud.project-id}")
//    private String projectId;
//
//    @Value("${google.cloud.location-id}")
//    private String locationId;
//
//    @Value("${gemini.model-name}")
//    private String modelName; // example: "gemini-pro-vision"
//
//    public Map<String, Object> extractDataFromPdf(MultipartFile file) throws IOException {
//        if (file == null || file.isEmpty()) {
//            throw new IllegalArgumentException("Uploaded file cannot be empty.");
//        }
//
//        byte[] fileBytes = file.getBytes();
//
//        try (VertexAI vertexAI = new VertexAI(projectId, locationId)) {
//
//            GenerativeModel model = new GenerativeModel(modelName, vertexAI);
//
//            // Build the PDF blob and part
//            Blob blob = Blob.newBuilder()
//                    .setMimeType("application/pdf")
//                    .setData(com.google.protobuf.ByteString.copyFrom(fileBytes))
//                    .build();
//
//            Part part = Part.newBuilder()
//                    .setInlineData(blob)
//                    .build();
//
//            Content content = Content.newBuilder()
//                    .addParts(part)
//                    .build();
//
//            GenerationConfig config = GenerationConfig.newBuilder()
//                    .setMaxOutputTokens(2048)
//                    .build();
//
//            // Prompt + PDF
//            GenerateContentResponse response = model.generateContent(
//                    Content.newBuilder()
//                            .addParts(Part.newBuilder().setText(
//                                    "Extract all the relevant information from this job description PDF to fill out a company details form. " +
//                                            "Include company name, batch (if mentioned), address details (block number, building name, area, landmark, state, city, pincode), " +
//                                            "contact person details (name, designation, email, mobile), designations offered, package, bond details, location for each designation, " +
//                                            "required qualifications for each designation (as a comma-separated list), and the placement process (round number, round name, description for each round). " +
//                                            "Return the information as a JSON object.")
//                            ).addParts(part).build()
//            );
//
//            String generatedText = response.getCandidates(0).getContent().getParts(0).getText();
//
//            // Convert to JSON
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.readValue(generatedText, new TypeReference<Map<String, Object>>() {});
//        } catch (JsonProcessingException e) {
//            System.err.println("Failed to parse response: " + e.getMessage());
//            return new HashMap<>();
//        }
//    }
//}
package Nency.project.Placement.Assistant.service;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class GeminiExtractionService {

    @Value("${jwt.token.secret}")  // Inject the API token from application.properties
    private String API_TOKEN;

    private static final String ENDPOINT = "https://api-inference.huggingface.co/models/mistralai/Mistral-7B-Instruct-v0.1";

    // Method to extract text from PDF and send it to Hugging Face
    public String extractCompanyDetailsFromJD(String jdText) throws IOException, InterruptedException {
        // Build the prompt based on the job description text
        String prompt = buildPrompt(jdText);

        // Send the prompt to Hugging Face API and get the response
        return sendToHuggingFace(prompt);
    }

    // Method to build the prompt
    private String buildPrompt(String jdText) {
        return "Extract the following fields from this job description and return as JSON:\n"
                + "{ \"name\": \"\", \"batch\": \"\", \"address\": { \"blockNo\": \"\", \"buildingName\": \"\", \"area\": \"\", \"landmark\": \"\", \"state\": \"\", \"city\": \"\", \"pincode\": \"\" },"
                + " \"contactPerson\": { \"name\": \"\", \"designation\": \"\", \"email\": \"\", \"mobile\": \"\" },"
                + " \"designations\": [ { \"designation\": \"\", \"Package\": \"\", \"bond\": \"\", \"location\": \"\", \"RequiredQualifications\": [],"
                + " \"placementProcess\": [ { \"roundNumber\": \"\", \"round\": \"\", \"description\": \"\" } ] } ] }\n\n"
                + "Job Description:\n" + jdText;
    }

    // Method to send the request to Hugging Face API and retrieve the response
    private String sendToHuggingFace(String prompt) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        // Create the HTTP request
        String jsonBody = buildRequestBody(prompt);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // If the request is successful, return the body, otherwise throw an exception
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new IOException("Hugging Face API request failed: " + response.statusCode() + " " + response.body());
        }
    }

    // Method to build the JSON body for the API request
    private String buildRequestBody(String prompt) throws IOException {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("inputs", prompt);

        // Use ObjectMapper to convert the map into a JSON string
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(requestBody);
    }
}
