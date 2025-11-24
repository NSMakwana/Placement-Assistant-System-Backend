package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.Poll;
import Nency.project.Placement.Assistant.model.PollResponse;
import Nency.project.Placement.Assistant.model.Student;
import Nency.project.Placement.Assistant.repository.PollResponseRepository;
import Nency.project.Placement.Assistant.repository.StudentRepository;
import Nency.project.Placement.Assistant.service.PollService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3002","https://placement-assistant-system.vercel.app"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/polls")
public class PollController {

    private final PollService pollService;
    private final PollResponseRepository pollResponseRepository;
    private final StudentRepository studentRepository;

    public PollController(PollService pollService, PollResponseRepository pollResponseRepository,StudentRepository studentRepository) {
        this.pollService = pollService;
        this.pollResponseRepository = pollResponseRepository;
        this.studentRepository = studentRepository;
    }

    // Create a poll
    @PostMapping("/create")
    public ResponseEntity<?> createPoll(@RequestBody Poll poll) {
        try {
            Poll savedPoll = pollService.createPoll(poll);
            return ResponseEntity.ok(savedPoll);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error creating poll: " + e.getMessage());
        }
    }

    // Get all polls
    @GetMapping("/all")
    public ResponseEntity<List<Poll>> getAllPolls() {
        return ResponseEntity.ok(pollService.getAllPolls());
    }

    // Stop a poll
    @PutMapping("/stop/{pollId}")
    public ResponseEntity<?> stopPoll(@PathVariable String pollId) {
        try {
            Poll poll = pollService.getPollById(pollId);
            if (poll != null) {
                poll.setActive(false);
                pollService.savePoll(poll);
                return ResponseEntity.ok("Poll stopped successfully!");
            } else {
                return ResponseEntity.status(404).body("Poll not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error stopping poll: " + e.getMessage());
        }
    }
    @PostMapping("/submit/{pollId}")
    public ResponseEntity<?> submitPollResponse(@PathVariable String pollId,
                                                @RequestBody PollResponse response) {
        try {
            // Attach pollId
            response.setPollId(pollId);
            // Save response
            PollResponse saved = pollResponseRepository.save(response);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error submitting poll response: " + e.getMessage());
        }
    }

    // Optional: Get all responses for a poll
    @GetMapping("/responses/{pollId}")
    public ResponseEntity<List<PollResponse>> getPollResponses(@PathVariable String pollId) {
        return ResponseEntity.ok(pollResponseRepository.findByPollId(pollId));
    }
    @GetMapping("/results/{pollId}")
    public ResponseEntity<?> getPollResults(@PathVariable String pollId) {
        List<PollResponse> responses = pollResponseRepository.findByPollId(pollId);

        // Group by answer → count per option
        Map<String, Long> aggregated =
                responses.stream().collect(
                        Collectors.groupingBy(
                                PollResponse::getAnswer,
                                Collectors.counting()
                        )
                );

        // Convert map → array list
        List<Map<String, Object>> resultList = new ArrayList<>();

        aggregated.forEach((option, count) -> {
            Map<String, Object> entry = new HashMap<>();
            entry.put("option", option);
            entry.put("count", count);
            resultList.add(entry);
        });

        return ResponseEntity.ok(resultList);
    }
    @GetMapping("/responses/detailed/{pollId}")
    public ResponseEntity<?> getDetailedPollResponses(@PathVariable String pollId) {
        List<PollResponse> responses = pollResponseRepository.findByPollId(pollId);

        List<Map<String, Object>> detailedResponses = new ArrayList<>();

        for (PollResponse r : responses) {
            Map<String, Object> map = new HashMap<>();
            map.put("answer", r.getAnswer());

            // Fetch student details from Student table using userId
            Student student = studentRepository.findByUserId(r.getStudentId());

            if (student != null) {
                map.put("studentName", student.getName());
                map.put("email", student.getEmail());
                map.put("course", student.getCourse());
            } else {
                map.put("studentName", "Unknown");
                map.put("email", "Unknown");
                map.put("course", "Unknown");
            }

            detailedResponses.add(map);
        }

        return ResponseEntity.ok(detailedResponses);
    }

    // Get poll by ID
    @GetMapping("/{pollId}")
    public ResponseEntity<?> getPollById(@PathVariable String pollId) {
        try {
            Poll poll = pollService.getPollById(pollId);
            if (poll == null) {
                return ResponseEntity.status(404).body("Poll not found");
            }
            return ResponseEntity.ok(poll);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching poll: " + e.getMessage());
        }
    }


}
