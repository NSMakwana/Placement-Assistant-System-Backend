package Nency.project.Placement.Assistant.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import Nency.project.Placement.Assistant.model.Agreement;
public interface AgreementRepository extends MongoRepository<Agreement, String> {
}

