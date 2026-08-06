package xyz.skaerf.scarlet.services;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.skaerf.scarlet.events.TimeTickEvent;
import xyz.skaerf.scarlet.events.mgmt.EventSource;

@Component
public class ClockService {

    private final ApplicationEventPublisher eventPublisher;

    public ClockService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedRate = 1000)
    public void tick() {
        TimeTickEvent event = new TimeTickEvent(EventSource.CLOCK);
        eventPublisher.publishEvent(event);
    }
}
