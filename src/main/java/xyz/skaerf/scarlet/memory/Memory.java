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
    @Column(nullable = false)
    private Instant updatedAt;
    private Instant lastAccessedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemorySource source;

    protected Memory() {

    }

    public Memory(MemoryType type, String content, double confidence, double importance, MemorySource source) {
        validate(content, confidence, importance);
        Instant now = Instant.now();
        this.id = UUID.randomUUID();
        this.type = type;
        this.content = content;
        this.confidence = confidence;
        this.importance = importance;
        this.source = source;
        this.createdAt = now;
        this.updatedAt = now;
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

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Instant getLastAccessedAt() {
        return this.lastAccessedAt;
    }

    public void setLastAccessedAt(Instant lastAccessedAt) {
        this.lastAccessedAt = lastAccessedAt;
    }

    public void update(MemoryType type, String content, double confidence, double importance, MemorySource source) {
        validate(content, confidence, importance);
        this.type = type;
        this.content = content;
        this.confidence = confidence;
        this.importance = importance;
        this.source = source;
        this.updatedAt = Instant.now();
    }

    private static void validate(String content, double confidence, double importance) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Memory content must not be blank");
        }
        validateScore("Confidence", confidence);
        validateScore("Importance", importance);
    }

    private static void validateScore(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be between 0.0 and 1.0");
        }
    }
}
