package Nency.project.Placement.Assistant.repository;
import Nency.project.Placement.Assistant.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>
{
        User findByEmail(String email); // For login validation
//        Optional<User> findByEmail(String email); // Returns an Optional<User>
}


