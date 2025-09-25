package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.Expense;
import Nency.project.Placement.Assistant.model.ExpenseItem;
import Nency.project.Placement.Assistant.repository.ExpenseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(
        origins = {
                "http://localhost:3000",
                "http://localhost:3002",
                "https://placement-assistant-system.vercel.app"
        },
        allowedHeaders = "*",
        allowCredentials = "true"
)
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    // ---------------- CREATE ----------------
    @PostMapping("/upload")
    public Expense uploadExpense(
            @RequestParam("batch") String batch,
            @RequestParam("companyName") String companyName,
            @RequestParam("billFile") MultipartFile billFile,
            @RequestParam("expenses") String expensesJson,
            @RequestParam("total") double total
    ) throws IOException {

        // Save file locally
        String fileName = System.currentTimeMillis() + "_" + billFile.getOriginalFilename();
        Path path = Paths.get("uploads/expenses/" + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, billFile.getBytes());

        // Parse expenses JSON -> List<ExpenseItem>
        ObjectMapper mapper = new ObjectMapper();
        List<ExpenseItem> expenseItems =
                Arrays.asList(mapper.readValue(expensesJson, ExpenseItem[].class));

        // Create Expense object
        Expense expense = new Expense();
        expense.setBatch(batch);
        expense.setCompanyName(companyName);
        expense.setFileName(fileName);
        expense.setFilePath(path.toString());
        expense.setExpenses(expenseItems);
        expense.setTotal(total);
        expense.setCreatedAt(LocalDateTime.now());
        expense.setUpdatedAt(LocalDateTime.now());

        return expenseRepository.save(expense);
    }

    // ---------------- READ ----------------
    @GetMapping("/{batch}/{companyName}")
    public List<Expense> getExpenses(@PathVariable String batch, @PathVariable String companyName) {
        return expenseRepository.findByBatchAndCompanyName(batch, companyName);
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable String id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return ResponseEntity.ok("Expense deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Expense not found");
        }
    }

    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(
            @PathVariable String id,
            @RequestBody Expense updatedExpense
    ) {
        return expenseRepository.findById(id).map(expense -> {
            expense.setExpenses(updatedExpense.getExpenses());
            expense.setTotal(updatedExpense.getTotal());
            expense.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(expenseRepository.save(expense));
        }).orElse(ResponseEntity.notFound().build());
    }
}
