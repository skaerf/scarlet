package xyz.skaerf.scarlet.world;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xyz.skaerf.scarlet.events.ApplicationFocusedEvent;
import xyz.skaerf.scarlet.events.TimeTickEvent;
import xyz.skaerf.scarlet.events.mgmt.EventSource;

import java.time.Instant;
import java.util.UUID;

@Component
public class WorldState {

    private volatile Snapshot snapshot;

    public WorldState() {
        Instant initialTime = Instant.now();
        FactProvenance initialProvenance = new FactProvenance(null, EventSource.SYSTEM, initialTime);
        this.snapshot = new Snapshot(initialTime, initialProvenance, "unknown", initialProvenance, "unknown", initialProvenance);
    }

    // EVENT LISTENERS

    @EventListener
    public synchronized void onTimeTick(TimeTickEvent event) {
        Snapshot current = this.snapshot;
        FactProvenance timeProvenance = new FactProvenance(event.getEventID(), event.getSource(), event.getOccurredAt());
        this.snapshot = new Snapshot(
                event.getOccurredAt(),
                timeProvenance,
                current.getActiveDevice(),
                current.activeDeviceProvenance(),
                current.getActiveApplication(),
                current.activeApplicationProvenance()
        );
    }

    @EventListener
    @Order(1)
    public synchronized void onApplicationFocused(ApplicationFocusedEvent event) {
        Snapshot current = this.snapshot;
        FactProvenance applicationProvenance = new FactProvenance(event.getEventID(), event.getSource(), event.getOccurredAt());
        this.snapshot = new Snapshot(
                current.getCurrentTime(),
                current.currentTimeProvenance(),
                event.getDeviceName(),
                applicationProvenance,
                event.getApplicationName(),
                applicationProvenance
        );
    }

    // GETTERS

    public Instant getCurrentTime() {
        return this.snapshot.getCurrentTime();
    }

    public String getActiveDevice() {
        return this.snapshot.getActiveDevice();
    }

    public String getActiveApplication() {
        return this.snapshot.getActiveApplication();
    }

    public Snapshot getSnapshot() {
        return this.snapshot;
    }

    public static final class Snapshot {

        private final Instant currentTime;
        private final FactProvenance currentTimeProvenance;
        private final String activeDevice;
        private final FactProvenance activeDeviceProvenance;
        private final String activeApplication;
        private final FactProvenance activeApplicationProvenance;

        private Snapshot(Instant currentTime, FactProvenance currentTimeProvenance, String activeDevice, FactProvenance activeDeviceProvenance, String activeApplication, FactProvenance activeApplicationProvenance) {
            this.currentTime = currentTime;
            this.currentTimeProvenance = currentTimeProvenance;
            this.activeDevice = activeDevice;
            this.activeDeviceProvenance = activeDeviceProvenance;
            this.activeApplication = activeApplication;
            this.activeApplicationProvenance = activeApplicationProvenance;
        }

        public Instant getCurrentTime() {
            return this.currentTime;
        }

        public String getActiveDevice() {
            return this.activeDevice;
        }

        public String getActiveApplication() {
            return this.activeApplication;
        }

        public FactProvenance currentTimeProvenance() {
            return this.currentTimeProvenance;
        }

        public FactProvenance activeDeviceProvenance() {
            return this.activeDeviceProvenance;
        }

        public FactProvenance activeApplicationProvenance() {
            return this.activeApplicationProvenance;
        }
    }

    public static final class FactProvenance {

        private final UUID eventId;
        private final EventSource source;
        private final Instant occurredAt;

        private FactProvenance(UUID eventId, EventSource source, Instant occurredAt) {
            this.eventId = eventId;
            this.source = source;
            this.occurredAt = occurredAt;
        }

        public UUID getEventId() {
            return this.eventId;
        }

        public EventSource getSource() {
            return this.source;
        }

        public Instant getOccurredAt() {
            return this.occurredAt;
        }
    }
}
