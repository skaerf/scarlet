package xyz.skaerf.scarlet.events.mgmt;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.skaerf.scarlet.events.TimeTickEvent;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventHistory {

    private final List<ScarletEvent> events;
    private final int maximumEvents;

    public EventHistory() {
        this.events = new ArrayList<>();
        this.maximumEvents = 100;
    }

    @EventListener
    public synchronized void onEvent(ScarletEvent event) {
        if (!(event instanceof TimeTickEvent)) {
            events.add(event);
            if (events.size() > this.maximumEvents) {
                events.removeFirst();
            }
        }
    }

    public synchronized List<ScarletEvent> getEvents() {
        return new ArrayList<>(events);
    }
}
