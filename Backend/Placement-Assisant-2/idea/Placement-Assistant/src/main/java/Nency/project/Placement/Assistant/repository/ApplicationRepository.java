package Nency.project.Placement.Assistant.repository;

import Nency.project.Placement.Assistant.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application> findByCompanyIdAndDesignation(String companyId, String designation);
}

