package xyz.skaerf.scarlet.llm;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class ToolRequest {

    private UUID requestId;
    private String toolName;
    private Map<String, Object> arguments;

    public ToolRequest() {
    }

    public ToolRequest(UUID requestId, String toolName, Map<String, Object> arguments) {
        this.requestId = requestId;
        this.toolName = toolName;
        setArguments(arguments);
    }

    public UUID getRequestId() {
        return this.requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public String getToolName() {
        return this.toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public Map<String, Object> getArguments() {
        return this.arguments;
    }

    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments == null ? null : new LinkedHashMap<>(arguments);
    }
}
