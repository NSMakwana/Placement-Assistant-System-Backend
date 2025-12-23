package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.RoundResult;
import Nency.project.Placement.Assistant.service.RoundResultService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/round-results")
public class RoundResultController {

    private final RoundResultService service;

    public RoundResultController(RoundResultService service) {
        this.service = service;
    }

    // Stage 1: Get applied students handled elsewhere (student repo filter). Here we accept selection
    @PostMapping("/select")
    public List<RoundResult> saveSelection(@RequestBody SelectionRequest req) {
        return service.saveSelection(req.getCompanyId(), req.getDesignation(), req.getBatch(),
                req.getRoundId(), req.getRoundName(), req.getStudentIds());
    }

    // Stage 2: Submit marks/status for single or bulk
    @PostMapping("/submit")
    public Object submitResults(@RequestBody Object payload) {
        // Accept two payload shapes:
        // 1) single RoundResult object
        // 2) { results: [RoundResult, ...] } for bulk
        if (payload instanceof Map) {
            Map map = (Map) payload;
            if (map.containsKey("results")) {
                List<Map> list = (List<Map>) map.get("results");
                List<RoundResult> saved = new java.util.ArrayList<>();
                for (Map m : list) {
                    RoundResult r = mapToRoundResult(m);
                    saved.add(service.submitResult(r));
                }
                return saved;
            }
        }
        // fallback: try mapping single
        RoundResult rr = mapToRoundResult((Map) payload);
        return service.submitResult(rr);
    }

    @GetMapping("/round/{roundId}")
    public List<RoundResult> getRoundResults(@PathVariable String roundId) {
        return service.getResultsForRound(roundId);
    }

    private RoundResult mapToRoundResult(Map m) {
        RoundResult r = new RoundResult();
        r.setId((String) m.get("id"));
        r.setCompanyId((String) m.get("companyId"));
        r.setDesignation((String) m.get("designation"));
        r.setBatch((String) m.get("batch"));
        r.setRoundId((String) m.get("roundId"));
        r.setRoundName((String) m.get("roundName"));
        r.setStudentId((String) m.get("studentId"));
        r.setStudentName((String) m.get("studentName"));
        r.setStudentEmail((String) m.get("studentEmail"));
        r.setStatus((String) m.get("status"));
        Object marks = m.get("marks");
        if (marks != null) {
            r.setMarks(Double.valueOf(String.valueOf(marks)));
        }
        r.setRemarks((String) m.get("remarks"));
        return r;
    }

    // DTO for Stage 1 selection
    public static class SelectionRequest {
        private String companyId;
        private String designation;
        private String batch;
        private String roundId;
        private String roundName;
        private java.util.List<String> studentIds;
        // getters/setters
        public String getCompanyId(){ return companyId;}
        public void setCompanyId(String companyId){ this.companyId = companyId;}
        public String getDesignation(){ return designation;}
        public void setDesignation(String designation){ this.designation = designation;}
        public String getBatch(){ return batch;}
        public void setBatch(String batch){ this.batch = batch;}
        public String getRoundId(){ return roundId;}
        public void setRoundId(String roundId){ this.roundId = roundId;}
        public String getRoundName(){ return roundName;}
        public void setRoundName(String roundName){ this.roundName = roundName;}
        public java.util.List<String> getStudentIds(){ return studentIds;}
        public void setStudentIds(java.util.List<String> studentIds){ this.studentIds = studentIds;}
    }
}
