package xyz.skaerf.scarlet.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.skaerf.scarlet.events.mgmt.EventSource;

@RestController
public class DesktopEventController {

    private final ApplicationEventPublisher eventPublisher;

    public DesktopEventController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/events/application-focused")
    public String applicationFocused(
            @RequestParam String device,
            @RequestParam String application
    ) {
        ApplicationFocusedEvent event =
                new ApplicationFocusedEvent(
                        EventSource.DESKTOP_AGENT,
                        device,
                        application
                );
        eventPublisher.publishEvent(event);
        return "Published event " +event.getEventID();
    }
}
