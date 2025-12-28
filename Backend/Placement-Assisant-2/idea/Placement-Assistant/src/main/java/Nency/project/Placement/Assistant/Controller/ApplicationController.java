package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.Application;
import Nency.project.Placement.Assistant.model.Student;
import Nency.project.Placement.Assistant.model.User;
import Nency.project.Placement.Assistant.payload.ApplicationRequest;
import Nency.project.Placement.Assistant.repository.ApplicationRepository;
import Nency.project.Placement.Assistant.repository.StudentRepository;
import Nency.project.Placement.Assistant.repository.UserRepository;
import Nency.project.Placement.Assistant.service.ApplicationService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(
        origins = {
                "http://localhost:3001",
                "http://localhost:3002",
                "http://localhost:3000",
                "https://placement-assistant-system.vercel.app"
        },
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class ApplicationController {

    private final ApplicationService service;
    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;

    public ApplicationController(
            ApplicationService service,
            ApplicationRepository applicationRepository,
            StudentRepository studentRepository
    ) {
        this.service = service;
        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
    }

    // ✅ Apply for company
    @PostMapping("/apply")
    public Application apply(@RequestBody ApplicationRequest req) {
        return service.apply(req);
    }

    // ✅ Student dashboard
    @GetMapping("/student/{studentId}")
    public List<Application> getStudentApplications(@PathVariable String studentId) {
        return service.getByStudent(studentId);
    }

    // ✅ Admin – get applied students
    @GetMapping("/{companyId}/{designation}")
    public List<Student> getAppliedStudents(
            @PathVariable String companyId,
            @PathVariable String designation) {

        List<Application> applications =
                applicationRepository.findByCompanyIdAndDesignation(companyId, designation);

        if (applications.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> studentIds = applications.stream()
                .map(Application::getStudentId)
                .toList();

        return studentRepository.findAllById(studentIds);
    }
}
