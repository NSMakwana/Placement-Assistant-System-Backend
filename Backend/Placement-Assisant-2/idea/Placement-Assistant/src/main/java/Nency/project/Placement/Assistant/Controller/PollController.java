package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.Poll;
import Nency.project.Placement.Assistant.service.NotificationService;
import Nency.project.Placement.Assistant.service.PollService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    private final PollService pollService;
    private  NotificationService notificationService;

    public PollController(PollService pollService, NotificationService notificationService) {
        this.pollService = pollService;
    }

    @PostMapping("/create")
    public Poll createPoll(@RequestBody Poll poll) {
        Poll savedPoll = pollService.savePoll(poll);
        notificationService.sendPollNotificationToBatch(savedPoll);
        return savedPoll;
    }

    @GetMapping("/all")
    public List<Poll> getAllPolls() {
        return pollService.getAllPolls();
    }

    @PutMapping("/stop/{id}")
    public ResponseEntity<Poll> stopPoll(@PathVariable String id) {
        Poll poll = pollService.stopPoll(id);
        return ResponseEntity.ok(poll);
    }

    @GetMapping("/{id}")
    public Poll getPoll(@PathVariable String id) {
        return pollService.getPoll(id).orElse(null);
    }
}
