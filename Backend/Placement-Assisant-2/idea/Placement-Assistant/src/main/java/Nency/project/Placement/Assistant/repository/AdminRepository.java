package Nency.project.Placement.Assistant.repository;

import Nency.project.Placement.Assistant.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Admin findByEmail(String email);
}
