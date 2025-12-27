package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.RoundResult;
import Nency.project.Placement.Assistant.service.RoundResultService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/results")
@CrossOrigin
public class RoundResultController {

    private final RoundResultService service;

    public RoundResultController(RoundResultService service) {
        this.service = service;
    }

    // Submit result for ONE student (Page 3)
    @PostMapping("/submit")
    public RoundResult submitResult(@RequestBody RoundResult result) {
        return service.submitResult(result);
    }

    // Optional: get all results of a student
    @GetMapping("/student/{studentId}")
    public java.util.List<RoundResult> getByStudent(@PathVariable String studentId) {
        return service.getResultsByStudent(studentId);
    }
}
