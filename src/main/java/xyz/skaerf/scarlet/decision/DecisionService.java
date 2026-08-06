package xyz.skaerf.scarlet.decision;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xyz.skaerf.scarlet.events.ApplicationFocusedEvent;
import xyz.skaerf.scarlet.world.WorldState;

@Component
public class DecisionService {

    private final WorldState worldState;
    private Decision latestDecision;
    private final ApplicationEventPublisher eventPublisher;

    public DecisionService(WorldState worldState, ApplicationEventPublisher eventPublisher) {
        this.worldState = worldState;
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    @Order(2)
    public void onApplicationFocused(ApplicationFocusedEvent event) {
        String activeApplication = worldState.getActiveApplication();
        Decision decision;
        if (activeApplication.equalsIgnoreCase("IntelliJ IDEA")) {
            decision = new Decision(event.getEventID(), DecisionType.NOTHING, "The user appears to be programming, so no interruption is needed.");
        }
        else {
            decision = new Decision(event.getEventID(), DecisionType.REASON, "There is no deterministic rule for the active application");
        }
        setLatestDecision(decision);
        eventPublisher.publishEvent(decision);
        System.out.println("Decision made: " +decision.getType()+" : "+decision.getReason());
    }

    private synchronized void setLatestDecision(Decision decision) {
        this.latestDecision = decision;
    }

    public synchronized Decision getLatestDecision() {
        return latestDecision;
    }
}
