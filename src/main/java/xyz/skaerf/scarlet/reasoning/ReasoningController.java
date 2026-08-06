package xyz.skaerf.scarlet.reasoning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReasoningController {

    private final ReasoningService reasoningService;

    public ReasoningController(ReasoningService reasoningService) {
        this.reasoningService = reasoningService;
    }

    @GetMapping("/reasoning/latest")
    public ReasoningRequest getLatestRequest() {
        return reasoningService.getLatestRequest();
    }
}
