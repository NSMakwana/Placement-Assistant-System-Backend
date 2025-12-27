package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.Application;
import Nency.project.Placement.Assistant.model.User;
import Nency.project.Placement.Assistant.repository.ApplicationRepository;
import Nency.project.Placement.Assistant.repository.UserRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{companyId}/{designation}")
    public List<User> getAppliedStudents(
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

        return userRepository.findAllById(studentIds);
    }


    @PostMapping("/apply")
    public void apply(@RequestBody Application app) {
        applicationRepository.save(app);
    }

}

