package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.Notification;
import Nency.project.Placement.Assistant.model.Poll;
import Nency.project.Placement.Assistant.model.Student;
import Nency.project.Placement.Assistant.repository.NotificationRepository;
import Nency.project.Placement.Assistant.repository.PollRepository;
import Nency.project.Placement.Assistant.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollService {

    private final PollRepository pollRepository;
    private final StudentRepository studentRepository;
    private final NotificationRepository notificationRepository;

    public PollService(PollRepository pollRepository,
                       StudentRepository studentRepository,
                       NotificationRepository notificationRepository) {
        this.pollRepository = pollRepository;
        this.studentRepository = studentRepository;
        this.notificationRepository = notificationRepository;
    }

    // Create poll and send notification to batch
    public Poll createPoll(Poll poll) {
        Poll savedPoll = pollRepository.save(poll);

        // Fetch students of the batch
        List<Student> students = studentRepository.findByBatch(poll.getBatch());

        for (Student s : students) {
            Notification n = new Notification();
            n.setTitle("New Poll from " + poll.getCompanyName());
            n.setMessage(poll.getQuestion());
            n.setPollId(savedPoll.getId());
            n.setStudentId(s.getUserId());
            n.setRead(false);
            notificationRepository.save(n);
        }

        return savedPoll;
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Poll getPollById(String pollId) {
        return pollRepository.findById(pollId).orElse(null);
    }

    public Poll savePoll(Poll poll) {
        return pollRepository.save(poll);
    }
}
