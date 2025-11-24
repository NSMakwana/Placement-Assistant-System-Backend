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

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
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

        List<Student> students = StudentRepository.findByBatch(poll.getBatch());

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
