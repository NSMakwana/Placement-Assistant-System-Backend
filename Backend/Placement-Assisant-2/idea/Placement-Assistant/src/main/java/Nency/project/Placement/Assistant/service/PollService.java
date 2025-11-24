package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.Poll;
import Nency.project.Placement.Assistant.repository.PollRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    private final PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Poll savePoll(Poll poll) {
        poll.setActive(true); // ensure new poll is active
        return pollRepository.save(poll);
    }

    public Optional<Poll> getPoll(String id) {
        return pollRepository.findById(id);
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Poll stopPoll(String id) {
        Poll poll = pollRepository.findById(id).orElseThrow(() -> new RuntimeException("Poll not found"));
        poll.setActive(false);
        return pollRepository.save(poll);
    }
}
