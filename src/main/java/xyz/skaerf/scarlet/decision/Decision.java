package xyz.skaerf.scarlet.decision;

import java.time.Instant;
import java.util.UUID;

public class Decision {

    private final UUID id;
    private final UUID triggeringEventID;
    private final DecisionType type;
    private final String reason;
    private final Instant createdAt;

    public Decision(UUID triggeringEventID, DecisionType type, String reason) {
        this.id = UUID.randomUUID();
        this.triggeringEventID = triggeringEventID;
        this.type = type;
        this.reason = reason;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getTriggeringEventID() {
        return this.triggeringEventID;
    }

    public DecisionType getType() {
        return this.type;
    }

    public String getReason() {
        return this.reason;
    }
    public Instant getCreatedAt() {
        return this.createdAt;
    }
}
