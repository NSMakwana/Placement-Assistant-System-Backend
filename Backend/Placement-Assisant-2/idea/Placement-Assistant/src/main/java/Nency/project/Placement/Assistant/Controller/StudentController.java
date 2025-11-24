//package Nency.project.Placement.Assistant.Controller;
//
//
//import Nency.project.Placement.Assistant.model.Student;
//import Nency.project.Placement.Assistant.service.StudentService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class StudentController {
//    private final StudentService studentService;
//
//    public StudentController(StudentService studentService) {
//        this.studentService = studentService;
//    }
//
//    // GET API to fetch students with optional filters
//    @GetMapping("/api/Student")
//    public List<Student> getStudents(
//            @RequestParam(required = false) String batch,
//            @RequestParam(required = false) String course) {
//        return studentService.getStudents(batch, course);
//    }
//}
//
package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Nency.project.Placement.Assistant.model.Student;
import Nency.project.Placement.Assistant.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins ={"http://localhost:3002","https://placement-assistant-system.vercel.app","http://localhost:3000"})
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @Autowired
    private StudentRepository studentRepository;


    // Fetch all students
    @GetMapping
    public List<Student> getAllStudents() {
        logger.info("Fetching all students...");
        List<Student> students = studentService.getAllStudents();
//        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            logger.warn("No students found in the database.");
        } else {
            logger.info("Found {} students.", students.size());
        }
        return students;
    }

    // Search students by name
    @GetMapping("/search")
    public List<Student> searchStudents(@RequestParam String name) {
        logger.info("Searching for students with name: {}", name);
        List<Student> students = studentService.searchStudentsByName(name);
        if (students.isEmpty()) {
            logger.warn("No students found with name: {}", name);
        } else {
            logger.info("Found {} students with name: {}", students.size(), name);
        }
        return students;
    }

    // Filter students by batch
    @GetMapping("/batch")
    public List<Student> getStudentsByBatch(@RequestParam String batch) {
        logger.info("Fetching students for batch: {}", batch);
        List<Student> students = studentService.getStudentsByBatch(batch);
        if (students.isEmpty()) {
            logger.warn("No students found for batch: {}", batch);
        } else {
            logger.info("Found {} students for batch: {}", students.size(), batch);
        }
        return students;
    }

    // Filter students by program
    @GetMapping("/program")
    public List<Student> getStudentsByProgram(@RequestParam String program) {
        logger.info("Fetching students for program: {}", program);
        List<Student> students = studentService.getStudentsByCourse(program);
        if (students.isEmpty()) {
            logger.warn("No students found for program: {}", program);
        } else {
            logger.info("Found {} students for program: {}", students.size(), program);
        }
        return students;
    }
    @GetMapping("/test")
    public String testMongoConnection() {
        List<Student> count = studentRepository.findAll();
        return "Total students in database: " + count;
    }

    @GetMapping("/batchByEmail/{email}")
    public ResponseEntity<?> getBatchByEmail(@PathVariable String email) {
        Optional<Student> studentOpt = studentRepository.findByEmail(email);
        if (studentOpt.isPresent()) {
            String batch = studentOpt.get().getBatch();
            return ResponseEntity.ok(batch);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }
    }
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student savedStudent = studentService.saveStudent(student);
        return ResponseEntity.ok(savedStudent);
    }

}


