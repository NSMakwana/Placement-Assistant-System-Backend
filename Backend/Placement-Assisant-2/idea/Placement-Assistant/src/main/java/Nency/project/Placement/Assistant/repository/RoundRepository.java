package Nency.project.Placement.Assistant.repository;

import Nency.project.Placement.Assistant.model.Round;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoundRepository extends MongoRepository<Round, String> {
    List<Round> findByCompanyIdAndDesignationAndBatchOrderBySequence(String companyId, String designation, String batch);
    List<Round> findByCompanyIdAndDesignationAndBatch(String companyId, String designation, String batch);
    List<Round> findByCompanyIdAndDesignation(String companyId, String designation);
}
