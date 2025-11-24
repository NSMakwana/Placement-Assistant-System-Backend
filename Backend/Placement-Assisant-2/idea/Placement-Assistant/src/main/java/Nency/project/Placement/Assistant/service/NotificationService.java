package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.Notification;
import Nency.project.Placement.Assistant.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    // Create a notification
    public Notification createNotification(Notification notification) {
        notification.setRead(false);
        return repo.save(notification);
    }

    // Get notifications for a student (or general notifications)
    public List<Notification> getStudentNotifications(String studentId) {
        return repo.findByStudentIdOrStudentIdIsNullOrderByCreatedAtDesc(studentId);
    }

    // Mark a notification as read
    public void markAsRead(String notificationId) {
        repo.findById(notificationId).ifPresent(n -> {
            n.setRead(true);
            repo.save(n);
        });
    }
}
