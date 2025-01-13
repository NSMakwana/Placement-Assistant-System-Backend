package Nency.project.Placement.Assistant.repository;

import Nency.project.Placement.Assistant.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
    List<Company> findByName(String name);
    List<Company> findByBatch(String batch);

    List<Company> findByNameContainingIgnoreCase(String name);


}
