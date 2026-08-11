package xyz.skaerf.scarlet.memory;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "memories")
public class Memory {

    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemoryType type;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false)
    private double confidence;
    @Column(nullable = false)
    private double importance;
    @Column(nullable = false)
    private Instant createdAt;
    private Instant lastAccessedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemorySource source;

    protected Memory() {

    }

    public Memory(MemoryType type, String content, double confidence, double importance, MemorySource source) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.content = content;
        this.confidence = confidence;
        this.importance = importance;
        this.source = source;
        this.createdAt = Instant.now();
        this.lastAccessedAt = null;
    }

    public UUID getId() {
        return this.id;
    }

    public MemoryType getType() {
        return this.type;
    }

    public String getContent() {
        return this.content;
    }

    public double getConfidence() {
        return this.confidence;
    }

    public double getImportance() {
        return this.importance;
    }

    public MemorySource getSource() {
        return this.source;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getLastAccessedAt() {
        return this.lastAccessedAt;
    }

    public void setLastAccessedAt(Instant lastAccessedAt) {
        this.lastAccessedAt = lastAccessedAt;
    }
}
