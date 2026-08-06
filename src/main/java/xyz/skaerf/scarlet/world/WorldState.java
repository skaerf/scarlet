package xyz.skaerf.scarlet.world;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xyz.skaerf.scarlet.events.ApplicationFocusedEvent;
import xyz.skaerf.scarlet.events.TimeTickEvent;

import java.time.Instant;

@Component
public class WorldState {

    private Instant currentTime;
    private String activeDevice;
    private String activeApplication;

    public WorldState() {
        this.currentTime = Instant.now();
        this.activeDevice = "unknown";
        this.activeApplication = "unknown";
    }

    // EVENT LISTENERS

    @EventListener
    public void onTimeTick(TimeTickEvent event) {
        this.currentTime = event.getOccurredAt();
    }

    @EventListener
    @Order(1)
    public void onApplicationFocused(ApplicationFocusedEvent event) {
        this.activeDevice = event.getDeviceName();
        this.activeApplication = event.getApplicationName();
    }

    // GETTERS

    public Instant getCurrentTime() {
        return this.currentTime;
    }

    public String getActiveDevice() {
        return this.activeDevice;
    }

    public String getActiveApplication() {
        return this.activeApplication;
    }
}
