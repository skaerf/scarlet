package xyz.skaerf.scarlet.reasoning;

import xyz.skaerf.scarlet.decision.Decision;
import xyz.skaerf.scarlet.memory.Memory;
import xyz.skaerf.scarlet.world.WorldState;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ReasoningRequest {

    private final UUID id;
    private final Decision decision;
    private final Instant worldTime;
    private final WorldState.FactProvenance currentTimeProvenance;
    private final String activeDevice;
    private final WorldState.FactProvenance activeDeviceProvenance;
    private final String activeApplication;
    private final WorldState.FactProvenance activeApplicationProvenance;
    private final List<Memory> memories;
    private final Instant createdAt;

    public ReasoningRequest(Decision decision, Instant worldTime, WorldState.FactProvenance currentTimeProvenance, String activeDevice, WorldState.FactProvenance activeDeviceProvenance, String activeApplication, WorldState.FactProvenance activeApplicationProvenance, List<Memory> memories) {
        this.id = UUID.randomUUID();
        this.decision = decision;
        this.worldTime = worldTime;
        this.currentTimeProvenance = currentTimeProvenance;
        this.activeDevice = activeDevice;
        this.activeDeviceProvenance = activeDeviceProvenance;
        this.activeApplication = activeApplication;
        this.activeApplicationProvenance = activeApplicationProvenance;
        this.memories = List.copyOf(memories);
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

    public WorldState.FactProvenance getCurrentTimeProvenance() {
        return this.currentTimeProvenance;
    }

    public String getActiveDevice() {
        return this.activeDevice;
    }

    public WorldState.FactProvenance getActiveDeviceProvenance() {
        return this.activeDeviceProvenance;
    }

    public String getActiveApplication() {
        return this.activeApplication;
    }

    public WorldState.FactProvenance getActiveApplicationProvenance() {
        return this.activeApplicationProvenance;
    }

    public List<Memory> getMemories() {
        return this.memories;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }
}
