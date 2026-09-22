package xyz.skaerf.scarlet.llm;

import org.springframework.stereotype.Component;
import xyz.skaerf.scarlet.decision.Decision;
import xyz.skaerf.scarlet.memory.Memory;
import xyz.skaerf.scarlet.reasoning.ReasoningRequest;
import xyz.skaerf.scarlet.world.WorldState;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class LlmRequestFactory {

    public LlmRequest create(ReasoningRequest reasoningRequest) {
        UUID requestId = UUID.randomUUID();
        Instant retrievedAt = Instant.now();
        String contextPrefix = "llm-request:" + requestId;

        TriggerContext triggerContext = createTriggerContext(reasoningRequest.getDecision(), contextPrefix, retrievedAt);
        WorldContext worldContext = createWorldContext(reasoningRequest, contextPrefix, retrievedAt);
        List<MemoryContext> memoryContext = createMemoryContext(reasoningRequest.getMemories(), contextPrefix, retrievedAt);

        return new LlmRequest(
                LlmRequest.CURRENT_SCHEMA_VERSION,
                requestId,
                retrievedAt,
                triggerContext,
                worldContext,
                memoryContext,
                List.of()
        );
    }

    private TriggerContext createTriggerContext(Decision decision, String contextPrefix, Instant retrievedAt) {
        Provenance provenance = new Provenance(
                contextPrefix + ":decision",
                ProvenanceOriginType.DECISION,
                "SCARLET",
                "decision-service",
                decision.getId().toString(),
                decision.getCreatedAt(),
                retrievedAt
        );

        return new TriggerContext(
                decision.getId(),
                decision.getTriggeringEventID(),
                decision.getType(),
                decision.getReason(),
                provenance
        );
    }

    private WorldContext createWorldContext(ReasoningRequest reasoningRequest, String contextPrefix, Instant retrievedAt) {
        Provenance currentTimeProvenance = createWorldProvenance(
                contextPrefix + ":world:current-time",
                reasoningRequest.getCurrentTimeProvenance(),
                retrievedAt
        );
        Provenance activeDeviceProvenance = createWorldProvenance(
                contextPrefix + ":world:active-device",
                reasoningRequest.getActiveDeviceProvenance(),
                retrievedAt
        );
        Provenance activeApplicationProvenance = createWorldProvenance(
                contextPrefix + ":world:active-application",
                reasoningRequest.getActiveApplicationProvenance(),
                retrievedAt
        );

        return new WorldContext(
                reasoningRequest.getWorldTime(),
                currentTimeProvenance,
                reasoningRequest.getActiveDevice(),
                activeDeviceProvenance,
                reasoningRequest.getActiveApplication(),
                activeApplicationProvenance
        );
    }

    private Provenance createWorldProvenance(String contextId, WorldState.FactProvenance factProvenance, Instant retrievedAt) {
        ProvenanceOriginType originType = factProvenance.getEventId() == null
                ? ProvenanceOriginType.SYSTEM
                : ProvenanceOriginType.EVENT;
        String originId = factProvenance.getEventId() == null
                ? null
                : factProvenance.getEventId().toString();

        return new Provenance(
                contextId,
                originType,
                "SCARLET",
                factProvenance.getSource().name(),
                originId,
                factProvenance.getOccurredAt(),
                retrievedAt
        );
    }

    private List<MemoryContext> createMemoryContext(List<Memory> memories, String contextPrefix, Instant retrievedAt) {
        List<MemoryContext> memoryContext = new ArrayList<>();
        for (Memory memory : memories) {
            Provenance provenance = new Provenance(
                    contextPrefix + ":memory:" + memory.getId(),
                    ProvenanceOriginType.MEMORY,
                    "SCARLET",
                    "postgresql.memories",
                    memory.getId().toString(),
                    null,
                    retrievedAt
            );
            memoryContext.add(new MemoryContext(
                    memory.getId(),
                    memory.getType(),
                    memory.getContent(),
                    memory.getConfidence(),
                    memory.getImportance(),
                    memory.getSource(),
                    memory.getCreatedAt(),
                    memory.getUpdatedAt(),
                    provenance
            ));
        }
        return memoryContext;
    }
}
