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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Value("${jwt.token.secret}") // Inject the Hugging Face API token
    private String API_TOKEN;

    private static final String ENDPOINT = "https://api-inference.huggingface.co/models/mistralai/Mistral-Nemo-Instruct-2407";

    public Map<String, Object> extractCompanyDetailsFromJD(String jdText) throws IOException, InterruptedException {
        String prompt = buildPrompt(jdText);
        String rawResponse = sendToHuggingFace(prompt);
        String json = extractJsonFromHuggingFaceResponse(rawResponse);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
    }

    private String buildPrompt(String jdText) {
        return """
                You are a helpful assistant. Your task is to extract structured company placement data from the following job description (JD) and return it in **valid JSON format** that matches this structure:

                {
                  "name": "",
                  "batch": "",
                  "address": {
                    "blockNo": "",
                    "buildingName": "",
                    "area": "",
                    "landmark": "",
                    "state": "",
                    "city": "",
                    "pincode": ""
                  },
                  "contactPerson": {
                    "name": "",
                    "designation": "",
                    "email": "",
                    "mobile": ""
                  },
                  "designations": [
                    {
                      "designation": "",
                      "Package": "",
                      "bond": "",
                      "location": "",
                      "requiredQualifications": [],
                      "placementProcess": [
                        {
                          "roundNumber": 1,
                          "round": "",
                          "description": ""
                        }
                      ]
                    }
                  ]
                }

                 Only return valid JSON. Do not include explanations, notes, or markdown formatting. Start your response with `{`.

                Here is the job description (JD):
                """ + jdText;
    }

    private String sendToHuggingFace(String prompt) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("inputs", prompt);
        String jsonBody = mapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new IOException("Hugging Face API request failed: " + response.statusCode() + " " + response.body());
        }
    }

    private String extractJsonFromHuggingFaceResponse(String rawResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // Parse the response JSON array: [{"generated_text": "..."}]
        var root = mapper.readTree(rawResponse);

        // Check if the response has the correct structure
        if (root.isArray() && !root.isEmpty() && root.get(0).has("generated_text")) {
            String generatedText = root.get(0).get("generated_text").asText();

            // Extract JSON substring from the generated text
            int firstBrace = generatedText.indexOf('{');
            int lastBrace = generatedText.lastIndexOf('}');
            if (firstBrace == -1 || lastBrace == -1 || firstBrace >= lastBrace) {
                throw new IOException("No valid JSON found in Hugging Face response.");
            }
            return generatedText.substring(firstBrace, lastBrace + 1);
        } else {
            throw new IOException("Unexpected response structure: " + rawResponse);
        }
    }
}
