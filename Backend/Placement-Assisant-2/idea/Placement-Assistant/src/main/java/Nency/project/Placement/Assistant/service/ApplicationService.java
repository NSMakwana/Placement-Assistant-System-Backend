package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.Application;
import Nency.project.Placement.Assistant.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository repo;

    public ApplicationService(ApplicationRepository repo) {
        this.repo = repo;
    }

    public Application apply(Application application) {
        boolean alreadyApplied = repo.existsByStudentIdAndCompanyId(
                application.getStudentId(),
                application.getCompanyId()
        );

        if (alreadyApplied) {
            throw new RuntimeException("Already applied");
        }

        return repo.save(application);
    }

    public List<Application> getByStudent(String studentId) {
        return repo.findByStudentId(studentId);
    }
}
