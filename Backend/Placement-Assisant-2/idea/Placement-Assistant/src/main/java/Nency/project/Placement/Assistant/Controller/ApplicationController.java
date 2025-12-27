package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.Application;
import Nency.project.Placement.Assistant.model.User;
import Nency.project.Placement.Assistant.repository.ApplicationRepository;
import Nency.project.Placement.Assistant.repository.UserRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{companyId}/{designationId}")
    public List<User> getAppliedStudents(
            @PathVariable String companyId,
            @PathVariable String designationId) {

        List<Application> applications =
                applicationRepository.findByCompanyIdAndDesignationId(
                        companyId, designationId);

        // extract student IDs
        List<String> studentIds = applications.stream()
                .map(Application::getStudentId)
                .toList();

        return userRepository.findAllById(studentIds);
    }
    @PostMapping("/apply")
    public void apply(@RequestBody Application app) {
        applicationRepository.save(app);
    }

}

