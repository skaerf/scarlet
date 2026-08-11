package xyz.skaerf.scarlet.events.mgmt;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="events")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "event_type")
public class ScarletEvent {

    @Id
    private UUID eventID;
    @Column(nullable = false)
    private Instant occurredAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventSource source;

    protected ScarletEvent() {

    }

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
