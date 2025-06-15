package Nency.project.Placement.Assistant.repository;

import Nency.project.Placement.Assistant.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
//    List<Company> findByName(String name);
    List<Company> findByBatch(String batch);
    Optional<Company> findByName(String name);
    List<Company> findByNameContainingIgnoreCase(String name);
    List<Company> findByVisibleTrue();



}
