package xyz.skaerf.scarlet.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.skaerf.scarlet.world.WorldState;

@RestController
public class WorldStateController {

    private final WorldState worldState;

    public WorldStateController(WorldState worldState) {
        this.worldState = worldState;
    }

    @GetMapping("/state/time")
    public String getCurrentTime() {
        return worldState.getCurrentTime().toString();
    }

    @GetMapping("/state/application")
    public String getActiveApplication() {
        WorldState.Snapshot snapshot = worldState.getSnapshot();
        return snapshot.getActiveDevice() + ": "+snapshot.getActiveApplication();
    }

    @GetMapping("/state")
    public WorldState.Snapshot getState() {
        return worldState.getSnapshot();
    }
}
