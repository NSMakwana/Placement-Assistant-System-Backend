package Nency.project.Placement.Assistant.repository;

import Nency.project.Placement.Assistant.model.PreplacementTalk;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PreplacementTalkRepository extends MongoRepository<PreplacementTalk, String> {
    List<PreplacementTalk> findByBatchAndCompanyName(String batch, String companyName);
}
