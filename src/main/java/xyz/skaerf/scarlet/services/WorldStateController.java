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
        return worldState.getActiveDevice() + ": "+worldState.getActiveApplication();
    }

    @GetMapping("/state")
    public WorldState getState() {
        return worldState;
    }
}
