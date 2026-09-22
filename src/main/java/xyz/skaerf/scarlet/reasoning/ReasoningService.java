package xyz.skaerf.scarlet.reasoning;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.skaerf.scarlet.decision.Decision;
import xyz.skaerf.scarlet.decision.DecisionType;
import xyz.skaerf.scarlet.llm.LlmRequest;
import xyz.skaerf.scarlet.llm.LlmRequestFactory;
import xyz.skaerf.scarlet.llm.LlmService;
import xyz.skaerf.scarlet.memory.Memory;
import xyz.skaerf.scarlet.memory.MemoryService;
import xyz.skaerf.scarlet.world.WorldState;

import java.util.List;

@Component
public class ReasoningService {

    private final WorldState worldState;
    private final MemoryService memoryService;
    private final LlmRequestFactory llmRequestFactory;
    private final LlmService llmService;
    private ReasoningRequest latestRequest;
    private LlmRequest latestLlmRequest;

    public ReasoningService(WorldState worldState, MemoryService memoryService, LlmRequestFactory llmRequestFactory, LlmService llmService) {
        this.worldState = worldState;
        this.memoryService = memoryService;
        this.llmRequestFactory = llmRequestFactory;
        this.llmService = llmService;
        this.latestRequest = null;
        this.latestLlmRequest = null;
    }

    @EventListener
    public void onDecision(Decision decision) {
        if (decision.getType() != DecisionType.REASON) {
            return;
        }
        WorldState.Snapshot snapshot = worldState.getSnapshot();
        List<Memory> memories = memoryService.getMemoriesForContext(snapshot.getActiveApplication(), 10);
        ReasoningRequest request = new ReasoningRequest(
                decision,
                snapshot.getCurrentTime(),
                snapshot.currentTimeProvenance(),
                snapshot.getActiveDevice(),
                snapshot.activeDeviceProvenance(),
                snapshot.getActiveApplication(),
                snapshot.activeApplicationProvenance(),
                memories
        );
        LlmRequest llmRequest = llmRequestFactory.create(request);
        setLatestRequests(request, llmRequest);
        llmService.process(llmRequest);
        System.out.print("Reasoning request created: "+request.getId());
    }

    private synchronized void setLatestRequests(ReasoningRequest request, LlmRequest llmRequest) {
        this.latestRequest = request;
        this.latestLlmRequest = llmRequest;
    }

    public synchronized ReasoningRequest getLatestRequest() {
        return this.latestRequest;
    }

    public synchronized LlmRequest getLatestLlmRequest() {
        return this.latestLlmRequest;
    }
}
