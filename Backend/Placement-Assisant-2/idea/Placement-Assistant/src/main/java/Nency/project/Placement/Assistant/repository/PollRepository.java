package Nency.project.Placement.Assistant.repository;

import Nency.project.Placement.Assistant.model.Poll;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PollRepository extends MongoRepository<Poll, String> {
    List<Poll> findByBatch(String batch);
}
