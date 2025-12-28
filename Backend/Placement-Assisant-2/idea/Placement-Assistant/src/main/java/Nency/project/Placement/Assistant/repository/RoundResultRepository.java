package Nency.project.Placement.Assistant.repository;

import Nency.project.Placement.Assistant.model.RoundResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoundResultRepository extends MongoRepository<RoundResult, String> {

    List<RoundResult> findByStudentId(String studentId);

    List<RoundResult> findByCompanyIdAndDesignation(String companyId, String designation);

    List<RoundResult> findByCompanyIdAndDesignationAndRoundName(
            String companyId,
            String designation,
            String roundName
    );


}
