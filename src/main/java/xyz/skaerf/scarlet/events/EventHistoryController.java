package xyz.skaerf.scarlet.events;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.skaerf.scarlet.events.mgmt.EventHistory;
import xyz.skaerf.scarlet.events.mgmt.ScarletEvent;

import java.util.List;

@RestController
public class EventHistoryController {

    private final EventHistory eventHistory;

    public EventHistoryController(EventHistory eventHistory) {
        this.eventHistory = eventHistory;
    }

    @GetMapping("/events")
    public List<ScarletEvent> getEvents() {
        return this.eventHistory.getEvents();
    }
}
