package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.Round;
import Nency.project.Placement.Assistant.service.RoundService;
import Nency.project.Placement.Assistant.service.RoundResultService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rounds")
public class RoundController {

    private final RoundService roundService;
    private final RoundResultService roundResultService;

    public RoundController(RoundService roundService, RoundResultService roundResultService) {
        this.roundService = roundService;
        this.roundResultService = roundResultService;
    }

    @GetMapping
    public List<Round> getRounds(@RequestParam String companyId,
                                 @RequestParam String designation,
                                 @RequestParam String batch) {
        return roundService.listRounds(companyId, designation, batch);
    }

    @PostMapping
    public Round createRound(@RequestBody Round round) {
        return roundService.createRound(round);
    }


}
