//package Nency.project.Placement.Assistant.service;
//
//
//import Nency.project.Placement.Assistant.model.Student;
//import Nency.project.Placement.Assistant.repository.StudentRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class StudentService {
//    private final StudentRepository studentRepository;
//
//    public StudentService(StudentRepository studentRepository) {
//        this.studentRepository = studentRepository;
//    }
//
//    public List<Student> getStudents(String batch, String course) {
//        if (batch != null && course != null) {
//            return studentRepository.findByBatchAndCourse(batch, course);
//        } else if (batch != null) {
//            return studentRepository.findByBatch(batch);
//        } else if (course != null) {
//            return studentRepository.findByCourse(course);
//        } else {
//            return studentRepository.findAll();
//        }
//    }
//}
package Nency.project.Placement.Assistant.service;
import Nency.project.Placement.Assistant.model.Student;
import Nency.project.Placement.Assistant.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Fetch all students
    public List<Student> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        students.forEach(student -> {
            if (student.getDob() == null) {
                System.out.println("Invalid or missing DOB for student: " + student.getName());
            }
        });
        return students;
    }

    // Fetch students by name (case-insensitive search)
    public List<Student> searchStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    // Fetch students by batch
    public List<Student> getStudentsByBatch(String batch) {
        return studentRepository.findByBatch(batch);
    }

    // Fetch students by program
    public List<Student> getStudentsByCourse(String course) {
        return studentRepository.findByCourse(course);
    }


}
