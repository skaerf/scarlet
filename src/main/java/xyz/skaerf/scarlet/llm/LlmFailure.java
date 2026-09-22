package xyz.skaerf.scarlet.llm;

import java.time.Instant;
import java.util.UUID;

public class LlmFailure {

    private UUID requestId;
    private Instant occurredAt;
    private String stage;
    private String message;

    public LlmFailure() {
    }

    public LlmFailure(UUID requestId, Instant occurredAt, String stage, String message) {
        this.requestId = requestId;
        this.occurredAt = occurredAt;
        this.stage = stage;
        this.message = message;
    }

    public UUID getRequestId() {
        return this.requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public Instant getOccurredAt() {
        return this.occurredAt;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getStage() {
        return this.stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
