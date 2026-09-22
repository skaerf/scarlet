package xyz.skaerf.scarlet.events;

import xyz.skaerf.scarlet.events.mgmt.EventSource;
import xyz.skaerf.scarlet.events.mgmt.ScarletEvent;

public class TimeTickEvent extends ScarletEvent {

    public TimeTickEvent() {
        super(EventSource.CLOCK);
    }
}
