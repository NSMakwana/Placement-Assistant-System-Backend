package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.Notification;
import Nency.project.Placement.Assistant.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Notification create(@RequestBody Notification notification) {
        return service.createNotification(notification);
    }

    @GetMapping("/student/{studentId}")
    public List<Notification> getStudentNotifications(@PathVariable String studentId) {
        return service.getStudentNotifications(studentId);
    }

    @PutMapping("/mark-read/{id}")
    public void markAsRead(@PathVariable String id) {
        service.markAsRead(id);
    }
}
