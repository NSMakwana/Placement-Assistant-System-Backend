package Nency.project.Placement.Assistant.repository;

import Nency.project.Placement.Assistant.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ApplicationRepository extends MongoRepository<Application, String> {
    boolean existsByStudentIdAndCompanyId(String studentId, String companyId);

    List<Application> findByStudentId(String studentId);

    List<Application> findByCompanyIdAndDesignation(String companyId, String designation);

}

