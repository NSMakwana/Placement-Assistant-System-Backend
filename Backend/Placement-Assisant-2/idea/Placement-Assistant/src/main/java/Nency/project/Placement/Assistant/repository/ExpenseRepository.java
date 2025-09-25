package Nency.project.Placement.Assistant.repository;

import Nency.project.Placement.Assistant.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
    List<Expense> findByBatchAndCompanyName(String batch, String companyName);
}
