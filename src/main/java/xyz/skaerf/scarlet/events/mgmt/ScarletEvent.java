package xyz.skaerf.scarlet.events.mgmt;

import java.time.Instant;
import java.util.UUID;

public class ScarletEvent {

    private final UUID eventID;
    private final Instant occurredAt;
    private final EventSource source;

    public ScarletEvent(EventSource source) {
        this.eventID = UUID.randomUUID();
        this.occurredAt = Instant.now();
        this.source = source;
    }

    public UUID getEventID() {
        return this.eventID;
    }

    public Instant getOccurredAt() {
        return this.occurredAt;
    }

    public EventSource getSource() {
        return this.source;
    }

}
