package xyz.skaerf.scarlet.events;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import xyz.skaerf.scarlet.events.mgmt.EventSource;
import xyz.skaerf.scarlet.events.mgmt.ScarletEvent;

@Entity
@DiscriminatorValue("APPLICATION_FOCUSED")
public class ApplicationFocusedEvent extends ScarletEvent {

    private String deviceName;
    private String applicationName;

    protected ApplicationFocusedEvent() {

    }

    public ApplicationFocusedEvent(EventSource source, String deviceName, String applicationName) {
        super(source);
        this.deviceName = deviceName;
        this.applicationName = applicationName;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getApplicationName() {
        return this.applicationName;
    }
}
