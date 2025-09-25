package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.PreplacementTalk;
import Nency.project.Placement.Assistant.repository.PreplacementTalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/preplacement-talks")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3002", "https://placement-assistant-system.vercel.app"})
public class PreplacementTalkController {

    private final Path root = Paths.get("uploads/preplacement");

    @Autowired
    private PreplacementTalkRepository preplacementTalkRepository;

    public PreplacementTalkController() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize storage folder!");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("batch") String batch,
                                        @RequestParam("companyName") String companyName,
                                        @RequestParam("file") MultipartFile file) {
        try {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = root.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            PreplacementTalk talk = new PreplacementTalk();
            talk.setBatch(batch);
            talk.setCompanyName(companyName);
            talk.setFileUrl(filePath.toString());
            talk.setUploadedAt(LocalDateTime.now());

            preplacementTalkRepository.save(talk);

            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/filter")
    public List<PreplacementTalk> getFiles(@RequestParam String batch, @RequestParam String companyName) {
        return preplacementTalkRepository.findByBatchAndCompanyName(batch, companyName);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id) {
        Optional<PreplacementTalk> talkOpt = preplacementTalkRepository.findById(id);
        if (talkOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(talkOpt.get().getFileUrl());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName().toString() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable String id) {
        Optional<PreplacementTalk> talkOpt = preplacementTalkRepository.findById(id);
        if (talkOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(talkOpt.get().getFileUrl());
            Files.deleteIfExists(filePath);
            preplacementTalkRepository.deleteById(id);

            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not delete file: " + e.getMessage());
        }
    }
}
