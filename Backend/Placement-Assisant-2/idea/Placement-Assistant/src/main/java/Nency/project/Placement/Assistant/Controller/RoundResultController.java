package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.RoundResult;
import Nency.project.Placement.Assistant.service.RoundResultService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/round-results")
public class RoundResultController {

    private final RoundResultService service;

    public RoundResultController(RoundResultService service) {
        this.service = service;
    }

    // Stage 1: Save selected students
    @PostMapping("/select")
    public List<RoundResult> saveSelection(@RequestBody SelectionRequest req) {
        return service.saveSelection(
                req.getCompanyId(),
                req.getDesignation(),
                req.getBatch(),
                req.getRoundNo(),
                req.getRoundName(),
                req.getStudentIds(),
                req.isFinalRound()
        );
    }

    // Stage 2: Submit marks (single or bulk)
    @PostMapping("/submit")
    public Object submitResults(@RequestBody Object payload) {

        if (payload instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) payload;
            if (map.containsKey("results")) {
                List<Map<String, Object>> list =
                        (List<Map<String, Object>>) map.get("results");

                List<RoundResult> saved = new ArrayList<>();
                for (Map<String, Object> m : list) {
                    saved.add(service.submitResult(mapToRoundResult(m)));
                }
                return saved;
            }
        }

        return service.submitResult(mapToRoundResult((Map<String, Object>) payload));
    }
    @GetMapping("/round")
    public List<RoundResult> getRoundResults(
            @RequestParam String companyId,
            @RequestParam String designation,
            @RequestParam String roundNo
    ) {
        return service.getResultsForRound(companyId, designation, roundNo);
    }

    private RoundResult mapToRoundResult(Map<String, Object> m) {
        RoundResult r = new RoundResult();
        r.setId((String) m.get("id"));
        r.setCompanyId((String) m.get("companyId"));
        r.setDesignation((String) m.get("designation"));
        r.setBatch((String) m.get("batch"));
        r.setRoundNo((String) m.get("roundNo"));
        r.setRoundName((String) m.get("roundName"));
        r.setStudentId((String) m.get("studentId"));
        r.setStudentName((String) m.get("studentName"));
        r.setStudentEmail((String) m.get("studentEmail"));
        r.setStatus((String) m.get("status"));
        r.setRemarks((String) m.get("remarks"));
        r.setFinalRound(Boolean.TRUE.equals(m.get("finalRound")));

        if (m.get("marks") != null) {
            r.setMarks(Double.valueOf(m.get("marks").toString()));
        }

        return r;
    }

    // DTO
    public static class SelectionRequest {
        private String companyId;
        private String designation;
        private String batch;
        private String roundNo;
        private String roundName;
        private boolean finalRound;
        private List<String> studentIds;

        // getters & setters
        public String getCompanyId() { return companyId; }
        public String getDesignation() { return designation; }
        public String getBatch() { return batch; }
        public String getRoundNo() { return roundNo; }
        public String getRoundName() { return roundName; }
        public boolean isFinalRound() { return finalRound; }
        public List<String> getStudentIds() { return studentIds; }

        public void setCompanyId(String companyId) { this.companyId = companyId; }
        public void setDesignation(String designation) { this.designation = designation; }
        public void setBatch(String batch) { this.batch = batch; }
        public void setRoundNo(String roundNo) { this.roundNo = roundNo; }
        public void setRoundName(String roundName) { this.roundName = roundName; }
        public void setFinalRound(boolean finalRound) { this.finalRound = finalRound; }
        public void setStudentIds(List<String> studentIds) { this.studentIds = studentIds; }
    }
}
