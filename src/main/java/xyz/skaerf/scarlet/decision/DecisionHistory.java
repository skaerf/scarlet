package xyz.skaerf.scarlet.decision;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DecisionHistory {

    private final List<Decision> decisions;
    private final int maximumDecisions;

    public DecisionHistory() {
        this.decisions = new ArrayList<>();
        this.maximumDecisions = 100;
    }

    @EventListener
    public synchronized void onDecision(Decision decision) {
        decisions.add(decision);
        if (decisions.size() > maximumDecisions) {
            decisions.removeFirst();
        }
    }

    public synchronized List<Decision> getDecisions() {
        return new ArrayList<>(decisions);
    }
}
