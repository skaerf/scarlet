package xyz.skaerf.scarlet.llm;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LlmRequest {

    public static final String CURRENT_SCHEMA_VERSION = "1.2";

    private String schemaVersion;
    private UUID requestId;
    private Instant createdAt;
    private TriggerContext triggerContext;
    private WorldContext worldContext;
    private List<MemoryContext> memoryContext;
    private List<ToolDefinition> availableTools;

    public LlmRequest() {
        this.memoryContext = new ArrayList<>();
        this.availableTools = new ArrayList<>();
    }

    public LlmRequest(TriggerContext triggerContext, WorldContext worldContext, List<MemoryContext> memoryContext, List<ToolDefinition> availableTools) {
        this(CURRENT_SCHEMA_VERSION, UUID.randomUUID(), Instant.now(), triggerContext, worldContext, memoryContext, availableTools);
    }

    public LlmRequest(String schemaVersion, UUID requestId, Instant createdAt, TriggerContext triggerContext, WorldContext worldContext, List<MemoryContext> memoryContext, List<ToolDefinition> availableTools) {
        this.schemaVersion = schemaVersion;
        this.requestId = requestId;
        this.createdAt = createdAt;
        this.triggerContext = triggerContext;
        this.worldContext = worldContext;
        setMemoryContext(memoryContext);
        setAvailableTools(availableTools);
    }

    public String getSchemaVersion() {
        return this.schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public UUID getRequestId() {
        return this.requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public TriggerContext getTriggerContext() {
        return this.triggerContext;
    }

    public void setTriggerContext(TriggerContext triggerContext) {
        this.triggerContext = triggerContext;
    }

    public WorldContext getWorldContext() {
        return this.worldContext;
    }

    public void setWorldContext(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    public List<MemoryContext> getMemoryContext() {
        return this.memoryContext;
    }

    public void setMemoryContext(List<MemoryContext> memoryContext) {
        this.memoryContext = memoryContext == null ? new ArrayList<>() : new ArrayList<>(memoryContext);
    }

    public List<ToolDefinition> getAvailableTools() {
        return this.availableTools;
    }

    public void setAvailableTools(List<ToolDefinition> availableTools) {
        this.availableTools = availableTools == null ? new ArrayList<>() : new ArrayList<>(availableTools);
    }
}
