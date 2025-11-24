package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.Poll;
import Nency.project.Placement.Assistant.service.PollService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3002","https://placement-assistant-system.vercel.app"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/polls")
public class PollController {

    private final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
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
}
