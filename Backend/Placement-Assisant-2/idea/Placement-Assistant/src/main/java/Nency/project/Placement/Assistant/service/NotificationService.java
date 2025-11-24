package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.Notification;
import Nency.project.Placement.Assistant.model.Poll;
import Nency.project.Placement.Assistant.model.Student;
import Nency.project.Placement.Assistant.repository.NotificationRepository;
import Nency.project.Placement.Assistant.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repo;
    private final StudentRepository studentRepo;

    public NotificationService(NotificationRepository repo, StudentRepository studentRepo) {
        this.repo = repo;
        this.studentRepo = studentRepo;
    }

    public Notification createNotification(Notification notification) {
        notification.setRead(false);
        return repo.save(notification);
    }

    public List<Notification> getStudentNotifications(String studentId) {
        return repo.findByStudentIdOrStudentIdIsNullOrderByCreatedAtDesc(studentId);
    }

    public void markAsRead(String notificationId) {
        repo.findById(notificationId).ifPresent(n -> {
            n.setRead(true);
            repo.save(n);
        });
    }

    public void sendPollNotificationToBatch(Poll poll) {

        // ✔ FIXED — use injected studentRepo instead of static call
        List<Student> students = studentRepo.findByBatch(poll.getBatch());

        for (Student s : students) {
            Notification n = new Notification();
            n.setTitle("New Poll from " + poll.getCompanyName());
            n.setMessage(poll.getQuestion());
            n.setStudentId(s.getId());
            n.setPollId(poll.getId());
            n.setRead(false);

            repo.save(n);
        }
    }

}
