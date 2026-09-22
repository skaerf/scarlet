package xyz.skaerf.scarlet.llm;

import xyz.skaerf.scarlet.memory.MemorySource;
import xyz.skaerf.scarlet.memory.MemoryType;

import java.time.Instant;
import java.util.UUID;

public class MemoryContext {

    private UUID memoryId;
    private MemoryType type;
    private String content;
    private double confidence;
    private double importance;
    private MemorySource source;
    private Instant createdAt;
    private Instant updatedAt;
    private Provenance provenance;

    public MemoryContext() {
    }

    public MemoryContext(UUID memoryId, MemoryType type, String content, double confidence, double importance, MemorySource source, Instant createdAt, Instant updatedAt, Provenance provenance) {
        this.memoryId = memoryId;
        this.type = type;
        this.content = content;
        this.confidence = confidence;
        this.importance = importance;
        this.source = source;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.provenance = provenance;
    }

    public UUID getMemoryId() {
        return this.memoryId;
    }

    public void setMemoryId(UUID memoryId) {
        this.memoryId = memoryId;
    }

    public MemoryType getType() {
        return this.type;
    }

    public void setType(MemoryType type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getConfidence() {
        return this.confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public double getImportance() {
        return this.importance;
    }

    public void setImportance(double importance) {
        this.importance = importance;
    }

    public MemorySource getSource() {
        return this.source;
    }

    public void setSource(MemorySource source) {
        this.source = source;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Provenance getProvenance() {
        return this.provenance;
    }

    public void setProvenance(Provenance provenance) {
        this.provenance = provenance;
    }
}
