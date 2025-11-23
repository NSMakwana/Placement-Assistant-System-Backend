package Nency.project.Placement.Assistant.repository;

import Nency.project.Placement.Assistant.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByStudentIdOrStudentIdIsNullOrderByCreatedAtDesc(String studentId);
}
