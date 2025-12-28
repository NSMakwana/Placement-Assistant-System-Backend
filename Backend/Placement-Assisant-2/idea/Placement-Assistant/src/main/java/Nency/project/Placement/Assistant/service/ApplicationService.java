package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.Application;
import Nency.project.Placement.Assistant.model.Student;
import Nency.project.Placement.Assistant.payload.ApplicationRequest;
import Nency.project.Placement.Assistant.repository.ApplicationRepository;
import Nency.project.Placement.Assistant.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              StudentRepository studentRepository) {
        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
    }

    public Application apply(ApplicationRequest req) {

        Student student = studentRepository.findByUserId(req.getUserId());

        if (student == null) {
            throw new RuntimeException("Student not found for userId: " + req.getUserId());
        }

        boolean alreadyApplied =
                applicationRepository.existsByStudentIdAndCompanyId(
                        student.getId(), req.getCompanyId());

        if (alreadyApplied) {
            throw new RuntimeException("Already applied for this company");
        }

        Application app = new Application();
        app.setStudentId(student.getId());
        app.setStudentName(student.getName());
        app.setStudentEmail(student.getEmail());
        app.setCompanyId(req.getCompanyId());
        app.setDesignation(req.getDesignation());

        return applicationRepository.save(app);
    }

    public List<Application> getByStudent(String studentId) {
        return applicationRepository.findByStudentId(studentId);
    }
}
