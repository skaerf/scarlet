package xyz.skaerf.scarlet.llm;

import xyz.skaerf.scarlet.decision.DecisionType;

import java.util.UUID;

public class TriggerContext {

    private UUID decisionId;
    private UUID triggeringEventId;
    private DecisionType decisionType;
    private String reason;
    private Provenance provenance;

    public TriggerContext() {
    }

    public TriggerContext(UUID decisionId, UUID triggeringEventId, DecisionType decisionType, String reason, Provenance provenance) {
        this.decisionId = decisionId;
        this.triggeringEventId = triggeringEventId;
        this.decisionType = decisionType;
        this.reason = reason;
        this.provenance = provenance;
    }

    public UUID getDecisionId() {
        return this.decisionId;
    }

    public void setDecisionId(UUID decisionId) {
        this.decisionId = decisionId;
    }

    public UUID getTriggeringEventId() {
        return this.triggeringEventId;
    }

    public void setTriggeringEventId(UUID triggeringEventId) {
        this.triggeringEventId = triggeringEventId;
    }

    public DecisionType getDecisionType() {
        return this.decisionType;
    }

    public void setDecisionType(DecisionType decisionType) {
        this.decisionType = decisionType;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Provenance getProvenance() {
        return this.provenance;
    }

    public void setProvenance(Provenance provenance) {
        this.provenance = provenance;
    }
}
