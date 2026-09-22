package xyz.skaerf.scarlet.llm;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LlmResponseValidator {

    public void validate(LlmResponse response, LlmRequest request) {
        if (response == null) {
            throw invalid("Response must not be null");
        }
        if (request == null) {
            throw invalid("Originating request must not be null");
        }
        if (response.getResponseType() == null) {
            throw invalid("responseType must be present");
        }

        switch (response.getResponseType()) {
            case FINAL_RESPONSE -> validateFinalResponse(response, request);
            case TOOL_REQUEST -> validateToolRequest(response, request);
        }
    }

    private void validateFinalResponse(LlmResponse response, LlmRequest request) {
        if (response.getFinalResponse() == null) {
            throw invalid("finalResponse must be present when responseType is FINAL_RESPONSE");
        }
        if (response.getToolRequest() != null) {
            throw invalid("toolRequest must be null when responseType is FINAL_RESPONSE");
        }

        FinalResponse finalResponse = response.getFinalResponse();
        if (isBlank(finalResponse.getReason())) {
            throw invalid("finalResponse.reason must be non-blank");
        }
        if (finalResponse.getCommunicationIntent() == null) {
            throw invalid("finalResponse.communicationIntent must be present");
        }
        if (finalResponse.getCommunicationIntent() != CommunicationIntent.NONE && isBlank(finalResponse.getContent())) {
            throw invalid("finalResponse.content must be non-blank when communication is requested");
        }
        validateEvidence(finalResponse.getEvidenceContextIds(), request);
    }

    private void validateEvidence(List<String> evidenceContextIds, LlmRequest request) {
        if (evidenceContextIds == null) {
            throw invalid("finalResponse.evidenceContextIds must be present, but may be empty");
        }

        Set<String> validContextIds = collectValidContextIds(request);
        Set<String> responseContextIds = new HashSet<>();
        for (String contextId : evidenceContextIds) {
            if (isBlank(contextId) || !validContextIds.contains(contextId)) {
                throw invalid("Unknown evidence context ID: " + contextId);
            }
            if (!responseContextIds.add(contextId)) {
                throw invalid("Duplicate evidence context ID: " + contextId);
            }
        }
    }

    private Set<String> collectValidContextIds(LlmRequest request) {
        Set<String> contextIds = new HashSet<>();

        if (request.getTriggerContext() != null) {
            addContextId(contextIds, request.getTriggerContext().getProvenance());
        }
        if (request.getWorldContext() != null) {
            WorldContext world = request.getWorldContext();
            addContextId(contextIds, world.getCurrentTimeProvenance());
            addContextId(contextIds, world.getActiveDeviceProvenance());
            addContextId(contextIds, world.getActiveApplicationProvenance());
        }
        if (request.getMemoryContext() != null) {
            for (MemoryContext memory : request.getMemoryContext()) {
                if (memory != null) {
                    addContextId(contextIds, memory.getProvenance());
                }
            }
        }

        return contextIds;
    }

    private void addContextId(Set<String> contextIds, Provenance provenance) {
        if (provenance != null && !isBlank(provenance.getContextId())) {
            contextIds.add(provenance.getContextId());
        }
    }

    private void validateToolRequest(LlmResponse response, LlmRequest request) {
        if (response.getToolRequest() == null) {
            throw invalid("toolRequest must be present when responseType is TOOL_REQUEST");
        }
        if (response.getFinalResponse() != null) {
            throw invalid("finalResponse must be null when responseType is TOOL_REQUEST");
        }

        ToolRequest toolRequest = response.getToolRequest();
        if (toolRequest.getRequestId() == null) {
            throw invalid("toolRequest.requestId must be present");
        }
        if (toolRequest.getArguments() == null) {
            throw invalid("toolRequest.arguments must be present");
        }

        boolean advertised = false;
        if (request.getAvailableTools() != null && toolRequest.getToolName() != null) {
            for (ToolDefinition tool : request.getAvailableTools()) {
                if (tool != null && toolRequest.getToolName().equals(tool.getName())) {
                    advertised = true;
                    break;
                }
            }
        }
        if (!advertised) {
            throw invalid("Requested tool was not advertised: " + toolRequest.getToolName());
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private IllegalArgumentException invalid(String message) {
        return new IllegalArgumentException("Invalid LLM response: " + message);
    }
}
