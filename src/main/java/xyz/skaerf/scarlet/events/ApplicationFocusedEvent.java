package xyz.skaerf.scarlet.events;

import xyz.skaerf.scarlet.events.mgmt.EventSource;
import xyz.skaerf.scarlet.events.mgmt.ScarletEvent;

public class ApplicationFocusedEvent extends ScarletEvent {

    private final String deviceName;
    private final String applicationName;

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
