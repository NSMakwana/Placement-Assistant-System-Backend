package Nency.project.Placement.Assistant.repository;

import Nency.project.Placement.Assistant.model.RoundResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoundResultRepository extends MongoRepository<RoundResult, String> {

    List<RoundResult> findByCompanyIdAndDesignationAndRoundNo(
            String companyId,
            String designation,
            String roundNo
    );

    List<RoundResult> findByStudentId(String studentId);

    void deleteByCompanyIdAndDesignationAndRoundNo(
            String companyId,
            String designation,
            String roundNo
    );
}
