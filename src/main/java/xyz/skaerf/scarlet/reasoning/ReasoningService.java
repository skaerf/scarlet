package xyz.skaerf.scarlet.reasoning;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.skaerf.scarlet.decision.Decision;
import xyz.skaerf.scarlet.decision.DecisionType;
import xyz.skaerf.scarlet.world.WorldState;

@Component
public class ReasoningService {

    private final WorldState worldState;
    private ReasoningRequest latestRequest;

    public ReasoningService(WorldState worldState) {
        this.worldState = worldState;
        this.latestRequest = null;
    }

    @EventListener
    public void onDecision(Decision decision) {
        if (decision.getType() != DecisionType.REASON) {
            return;
        }
        ReasoningRequest request = new ReasoningRequest(decision, worldState.getCurrentTime(), worldState.getActiveDevice(), worldState.getActiveApplication());
        setLatestRequest(request);
        System.out.print("Reasoning request created: "+request.getId());
    }

    private synchronized void setLatestRequest(ReasoningRequest request) {
        this.latestRequest = request;
    }

    public synchronized ReasoningRequest getLatestRequest() {
        return this.latestRequest;
    }
}
