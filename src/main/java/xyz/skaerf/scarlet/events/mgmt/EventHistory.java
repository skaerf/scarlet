package xyz.skaerf.scarlet.events.mgmt;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.skaerf.scarlet.events.TimeTickEvent;

import java.util.List;

@Component
public class EventHistory {

    private final EventRepository eventRepository;

    public EventHistory(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @EventListener
    public synchronized void onEvent(ScarletEvent event) {
        if (!(event instanceof TimeTickEvent)) {
            eventRepository.save(event);
        }
    }

    public synchronized List<ScarletEvent> getEvents() {
        return eventRepository.findAll();
    }
}
