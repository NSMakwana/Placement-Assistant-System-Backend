package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.Application;
import Nency.project.Placement.Assistant.model.Student;
import Nency.project.Placement.Assistant.model.User;
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
@CrossOrigin(origins = {"http://localhost:3001","http://localhost:3002","http://localhost:3000","https://placement-assistant-system.vercel.app"}, allowedHeaders = "*", allowCredentials = "true")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private StudentRepository studentRepository;


    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @PostMapping("/apply")
    public Application apply(@RequestBody Application application) {
        return service.apply(application);
    }

    @GetMapping("/student/{studentId}")
    public List<Application> getStudentApplications(@PathVariable String studentId) {
        return service.getByStudent(studentId);
    }
    
    @GetMapping("/{companyId}/{designation}")
    public List<Student> getAppliedStudents(
            @PathVariable String companyId,
            @PathVariable String designation) {

        System.out.println("Fetching applications for companyId: " + companyId + ", designation: " + designation);

        List<Application> applications = applicationRepository.findByCompanyIdAndDesignation(companyId, designation);

        if (applications == null || applications.isEmpty()) {
            System.out.println("No applications found");
            return Collections.emptyList();
        }

        System.out.println("Applications found: " + applications.size());

        List<String> studentIds = applications.stream()
                .map(Application::getStudentId)
                .toList();

        System.out.println("Student IDs: " + studentIds);

        return studentRepository.findAllById(studentIds);
    }




}

