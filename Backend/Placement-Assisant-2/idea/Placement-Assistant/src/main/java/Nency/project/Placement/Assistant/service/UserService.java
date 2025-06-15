package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.Student;
import Nency.project.Placement.Assistant.model.User;
import Nency.project.Placement.Assistant.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UserService {
    public void processStudentFile(MultipartFile file, String batch) throws IOException {
        List<Student> studentList = excelHelper.parseStudentExcel(file.getInputStream());

        for (Student s : studentList) {

            // Generate password
            String baseName = s.getName().length() >= 4 ? s.getName().substring(0, 4) : s.getName();
            String password = baseName.toLowerCase() + "_" + batch;

            // Save corresponding user
            User user = new User();
            user.setName(s.getName());
            user.setEno(s.getEno());
            user.setEmail(s.getEmail());
            user.setPassword(password);
            user.setRole("student");
            user.setHasSubmittedAgreement(true);
            UserRepository.save(user);
        }
    }

}
