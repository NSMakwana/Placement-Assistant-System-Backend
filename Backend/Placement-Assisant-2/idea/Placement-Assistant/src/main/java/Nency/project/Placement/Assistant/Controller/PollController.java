package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.Poll;
import Nency.project.Placement.Assistant.model.PollResponse;
import Nency.project.Placement.Assistant.service.NotificationService;
import Nency.project.Placement.Assistant.service.PollService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/polls")
@CrossOrigin
public class PollController {

    private final PollService pollService;
    private final NotificationService notificationService;

    public PollController(PollService pollService ,NotificationService notificationService) {
        this.pollService = pollService;
        this.notificationService = notificationService;
    }

    // ✔ CREATE POLL
    @PostMapping("/create")
    public Poll createPoll(@RequestBody Poll poll) {
        Poll savedPoll = pollService.savePoll(poll);

        // send poll notification to all students in the batch
        notificationService.sendPollNotificationToBatch(savedPoll);

        return savedPoll;
    }

    // ✔ LIST ALL POLLS (used in your frontend)
    @GetMapping("/all")
    public List<Poll> getAllPolls() {
        return pollService.getAllPolls();
    }

    // ✔ GET ONE POLL BY ID
    @GetMapping("/{id}")
    public Poll getPollById(@PathVariable String id) {
        return pollService.getPoll(id).orElse(null);
    }

    // ✔ STOP POLL (used by Stop button)
    @PutMapping("/stop/{pollId}")
    public Poll stopPoll(@PathVariable String pollId) {
        return pollService.stopPoll(pollId);
    }
}
