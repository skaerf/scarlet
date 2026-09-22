package xyz.skaerf.scarlet.llm;

import java.util.ArrayList;
import java.util.List;

public class FinalResponse {

    private String content;
    private String reason;
    private CommunicationIntent communicationIntent;
    private List<String> evidenceContextIds;

    public FinalResponse() {
    }

    public FinalResponse(String content, String reason, CommunicationIntent communicationIntent, List<String> evidenceContextIds) {
        this.content = content;
        this.reason = reason;
        this.communicationIntent = communicationIntent;
        setEvidenceContextIds(evidenceContextIds);
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public CommunicationIntent getCommunicationIntent() {
        return this.communicationIntent;
    }

    public void setCommunicationIntent(CommunicationIntent communicationIntent) {
        this.communicationIntent = communicationIntent;
    }

    public List<String> getEvidenceContextIds() {
        return this.evidenceContextIds;
    }

    public void setEvidenceContextIds(List<String> evidenceContextIds) {
        this.evidenceContextIds = evidenceContextIds == null ? null : new ArrayList<>(evidenceContextIds);
    }
}
