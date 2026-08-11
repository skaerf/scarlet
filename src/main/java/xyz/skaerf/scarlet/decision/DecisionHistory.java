package xyz.skaerf.scarlet.decision;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DecisionHistory {

    private final DecisionRepository decisionRepository;

    public DecisionHistory(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    @EventListener
    public synchronized void onDecision(Decision decision) {
        decisionRepository.save(decision);
    }

    public synchronized List<Decision> getDecisions() {
        return decisionRepository.findAll();
    }
}
