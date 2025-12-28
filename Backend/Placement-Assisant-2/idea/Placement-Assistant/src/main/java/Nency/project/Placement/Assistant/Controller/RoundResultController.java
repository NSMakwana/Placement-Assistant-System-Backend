package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.RoundResult;
import Nency.project.Placement.Assistant.service.RoundResultService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@CrossOrigin
public class RoundResultController {

    private final RoundResultService service;

    public RoundResultController(RoundResultService service) {
        this.service = service;
    }

    // Page 3 submit
    @PostMapping("/submit")
    public RoundResult submitResult(@RequestBody RoundResult result) {
        return service.submitResult(result);
    }

    // View Result Summary (company + round)
    @GetMapping("/summary")
    public List<RoundResult> getRoundSummary(
            @RequestParam String companyId,
            @RequestParam String designation,
            @RequestParam String roundName
    ) {
        return service.getResultsByCompanyAndRound(companyId, designation, roundName);
    }
    @GetMapping("/company")
    public List<RoundResult> getSummary(
            @RequestParam String companyId,
            @RequestParam String designation
    ) {
        return service.getResultsByCompanyAndDesignation(companyId, designation);
    }


    // Optional: student dashboard
    @GetMapping("/student/{studentId}")
    public List<RoundResult> getByStudent(@PathVariable String studentId) {
        return service.getResultsByStudent(studentId);
    }
}
