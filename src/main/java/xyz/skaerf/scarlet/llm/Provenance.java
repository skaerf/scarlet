package xyz.skaerf.scarlet.llm;

import java.time.Instant;

public class Provenance {

    private String contextId;
    private ProvenanceOriginType originType;
    private String provider;
    private String source;
    private String originId;
    private Instant observedAt;
    private Instant retrievedAt;

    public Provenance() {
    }

    public Provenance(String contextId, ProvenanceOriginType originType, String provider, String source, String originId, Instant observedAt, Instant retrievedAt) {
        this.contextId = contextId;
        this.originType = originType;
        this.provider = provider;
        this.source = source;
        this.originId = originId;
        this.observedAt = observedAt;
        this.retrievedAt = retrievedAt;
    }

    public String getContextId() {
        return this.contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public ProvenanceOriginType getOriginType() {
        return this.originType;
    }

    public void setOriginType(ProvenanceOriginType originType) {
        this.originType = originType;
    }

    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOriginId() {
        return this.originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public Instant getObservedAt() {
        return this.observedAt;
    }

    public void setObservedAt(Instant observedAt) {
        this.observedAt = observedAt;
    }

    public Instant getRetrievedAt() {
        return this.retrievedAt;
    }

    public void setRetrievedAt(Instant retrievedAt) {
        this.retrievedAt = retrievedAt;
    }
}
