package xyz.skaerf.scarlet.reasoning;

import xyz.skaerf.scarlet.decision.Decision;

import java.time.Instant;
import java.util.UUID;

public class ReasoningRequest {

    private final UUID id;
    private final Decision decision;
    private final Instant worldTime;
    private final String activeDevice;
    private final String activeApplication;
    private final Instant createdAt;

    public ReasoningRequest(Decision decision, Instant worldTime, String activeDevice, String activeApplication) {
        this.id = UUID.randomUUID();
        this.decision = decision;
        this.worldTime = worldTime;
        this.activeDevice = activeDevice;
        this.activeApplication = activeApplication;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return this.id;
    }

    public Decision getDecision() {
        return this.decision;
    }

    public Instant getWorldTime() {
        return this.worldTime;
    }

    public String getActiveDevice() {
        return this.activeDevice;
    }

    public String getActiveApplication() {
        return this.activeApplication;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }
}
