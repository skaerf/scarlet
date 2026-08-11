package xyz.skaerf.scarlet.decision;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "decisions")
public class Decision {

    @Id
    private UUID id;
    @Column(nullable = false)
    private UUID triggeringEventID;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DecisionType type;
    @Column(nullable = false)
    private String reason;
    @Column(nullable = false)
    private Instant createdAt;

    protected Decision() {
    }

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
