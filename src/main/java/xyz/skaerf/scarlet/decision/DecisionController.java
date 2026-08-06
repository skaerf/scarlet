package xyz.skaerf.scarlet.decision;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DecisionController {

    private final DecisionService decisionService;
    private final DecisionHistory decisionHistory;

    public DecisionController(DecisionService decisionService, DecisionHistory decisionHistory) {
        this.decisionService = decisionService;
        this.decisionHistory = decisionHistory;
    }

    @GetMapping("/decisions/latest")
    public Decision getLatestDecision() {
        return decisionService.getLatestDecision();
    }

    @GetMapping("/decisions")
    public List<Decision> getDecisions() {
        return decisionHistory.getDecisions();
    }
}
