package Nency.project.Placement.Assistant.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.Content;
import com.google.cloud.vertexai.api.Part;
import com.google.cloud.vertexai.api.Blob;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.generativeai.GenerativeModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class GeminiExtractionService {

    @Value("${google.cloud.project-id}")
    private String projectId;

    @Value("${google.cloud.location-id}")
    private String locationId;

    @Value("${gemini.model-name}")
    private String modelName; // example: "gemini-pro-vision"

    public Map<String, Object> extractDataFromPdf(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file cannot be empty.");
        }

        byte[] fileBytes = file.getBytes();

        try (VertexAI vertexAI = new VertexAI(projectId, locationId)) {

            GenerativeModel model = new GenerativeModel(modelName, vertexAI);

            // Build the PDF blob and part
            Blob blob = Blob.newBuilder()
                    .setMimeType("application/pdf")
                    .setData(com.google.protobuf.ByteString.copyFrom(fileBytes))
                    .build();

            Part part = Part.newBuilder()
                    .setInlineData(blob)
                    .build();

            Content content = Content.newBuilder()
                    .addParts(part)
                    .build();

            GenerationConfig config = GenerationConfig.newBuilder()
                    .setMaxOutputTokens(2048)
                    .build();

            // Prompt + PDF
            GenerateContentResponse response = model.generateContent(
                    Content.newBuilder()
                            .addParts(Part.newBuilder().setText(
                                    "Extract all the relevant information from this job description PDF to fill out a company details form. " +
                                            "Include company name, batch (if mentioned), address details (block number, building name, area, landmark, state, city, pincode), " +
                                            "contact person details (name, designation, email, mobile), designations offered, package, bond details, location for each designation, " +
                                            "required qualifications for each designation (as a comma-separated list), and the placement process (round number, round name, description for each round). " +
                                            "Return the information as a JSON object.")
                            ).addParts(part).build()
            );

            String generatedText = response.getCandidates(0).getContent().getParts(0).getText();

            // Convert to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(generatedText, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            System.err.println("Failed to parse response: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
