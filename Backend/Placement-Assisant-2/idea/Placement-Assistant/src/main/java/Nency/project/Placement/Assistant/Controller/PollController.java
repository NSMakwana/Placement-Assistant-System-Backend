package Nency.project.Placement.Assistant.Controller;
import Nency.project.Placement.Assistant.model.Poll;
import Nency.project.Placement.Assistant.model.PollResponse;
import Nency.project.Placement.Assistant.service.PollService;
import Nency.project.Placement.Assistant.service.NotificationService; // example usage
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/polls")
public class PollController {
    private final PollService pollService;
    private final NotificationService notificationService; // you already have notifications — use it


    public PollController(PollService pollService, NotificationService notificationService) {
        this.pollService = pollService;
        this.notificationService = notificationService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createPoll(@RequestBody Poll poll) {
// save poll
        Poll saved = pollService.savePoll(poll);


// send notification to students of batch
// This method assumes NotificationService has a helper to create per-student notifications by batch
        notificationService.sendPollNotificationToBatch(saved);


        return ResponseEntity.ok(Map.of("message","Poll created","pollId", saved.getId()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getPoll(@PathVariable String id) {
        return pollService.getPoll(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/submit")
    public ResponseEntity<?> submitResponse(@RequestBody PollResponse response) {
        PollResponse saved = pollService.submitResponse(response);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/responses/{pollId}")
    public ResponseEntity<List<PollResponse>> getResponses(@PathVariable String pollId) {
        return ResponseEntity.ok(pollService.getResponses(pollId));
    }
}