package Nency.project.Placement.Assistant.repository;


import Nency.project.Placement.Assistant.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    Optional<Student> findByEmail(String email) ;
    // Custom query methods
    List<Student> findByBatch(String batch); // Find students by batch
    List<Student> findByCourse(String course); // Find students by course
    List<Student> findByBatchAndCourse(String batch, String course); // Find by both

    List<Student> findByNameContainingIgnoreCase(String name);

    Student findByUserId(String userId);



}

