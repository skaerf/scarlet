package xyz.skaerf.scarlet.llm;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ToolResult {

    private UUID requestId;
    private String toolName;
    private boolean success;
    private Object result;
    private String error;
    private List<Provenance> provenance;

    public ToolResult() {
        this.provenance = new ArrayList<>();
    }

    public ToolResult(UUID requestId, String toolName, boolean success, Object result, String error, List<Provenance> provenance) {
        this.requestId = requestId;
        this.toolName = toolName;
        this.success = success;
        this.result = result;
        this.error = error;
        setProvenance(provenance);
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

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Provenance> getProvenance() {
        return this.provenance;
    }

    public void setProvenance(List<Provenance> provenance) {
        this.provenance = provenance == null ? new ArrayList<>() : new ArrayList<>(provenance);
    }
}
