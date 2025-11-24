package Nency.project.Placement.Assistant.service;
import Nency.project.Placement.Assistant.model.Poll;
import Nency.project.Placement.Assistant.model.PollResponse;
import Nency.project.Placement.Assistant.repository.PollRepository;
import Nency.project.Placement.Assistant.repository.PollResponseRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class PollService {
    private final PollRepository pollRepository;
    private final PollResponseRepository responseRepository;


    public PollService(PollRepository pollRepository, PollResponseRepository responseRepository) {
        this.pollRepository = pollRepository;
        this.responseRepository = responseRepository;
    }


    public Poll savePoll(Poll poll) {
        return pollRepository.save(poll);
    }


    public Optional<Poll> getPoll(String id) {
        return pollRepository.findById(id);
    }


    public List<PollResponse> getResponses(String pollId) {
        return responseRepository.findByPollId(pollId);
    }


    public PollResponse submitResponse(PollResponse r) {
// prevent duplicate response from same student for same poll
        List<PollResponse> existing = responseRepository.findByPollIdAndStudentId(r.getPollId(), r.getStudentId());
        if (!existing.isEmpty()) {
// update the first existing response
            PollResponse prev = existing.get(0);
            prev.setAnswer(r.getAnswer());
            prev.setSubmittedAt(java.time.LocalDateTime.now());
            return responseRepository.save(prev);
        }
        return responseRepository.save(r);
    }
    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Poll stopPoll(String pollId) {
        Poll poll = pollRepository.findById(pollId).orElse(null);
        if (poll != null) {
            poll.setActive(false);
            pollRepository.save(poll);
        }
        return poll;
    }

}
