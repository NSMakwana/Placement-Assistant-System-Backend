package Nency.project.Placement.Assistant.repository;

import Nency.project.Placement.Assistant.model.RoundResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoundResultRepository extends MongoRepository<RoundResult, String> {
    List<RoundResult> findByRoundId(String roundId);
    List<RoundResult> findByCompanyIdAndDesignationAndStudentId(String companyId, String designation, String studentId);
    void deleteByRoundId(String roundId);
    List<RoundResult> findByStudentId(String studentId);
}
