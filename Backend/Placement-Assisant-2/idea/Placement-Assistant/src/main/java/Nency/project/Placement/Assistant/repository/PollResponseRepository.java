package Nency.project.Placement.Assistant.repository;
import Nency.project.Placement.Assistant.model.PollResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface PollResponseRepository extends MongoRepository<PollResponse, String> {
    List<PollResponse> findByPollId(String pollId);
    List<PollResponse> findByStudentId(String studentId);
    List<PollResponse> findByPollIdAndStudentId(String pollId, String studentId);
}