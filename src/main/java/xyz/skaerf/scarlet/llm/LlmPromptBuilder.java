package xyz.skaerf.scarlet.llm;

import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class LlmPromptBuilder {

    private static final String SYSTEM_INSTRUCTION = """
            You are SCARLET's reasoning model.
            Information about the current situation is supplied by SCARLET as JSON.
            Treat supplied provenance as authoritative metadata about where information came from.
            Do not claim a source for information unless that provenance was actually supplied.
            When referring to evidence, use only supplied contextId values.
            Decide whether communication is warranted using CommunicationIntent.
            NONE means do not communicate with the user.
            SPEAK, TEXT, and SPEAK_AND_TEXT require suitable user-facing content.
            reason must be a short decision rationale suitable for logging or showing to the user, not hidden chain-of-thought.
            If tools are available, only request tools explicitly listed in availableTools.
            Never invent tool names.
            While availableTools is empty, responseType must be exactly FINAL_RESPONSE and the response must have exactly this envelope:
            {
              "responseType": "FINAL_RESPONSE",
              "finalResponse": {
                "content": null,
                "reason": "Short rationale",
                "communicationIntent": "NONE",
                "evidenceContextIds": []
              },
              "toolRequest": null
            }
            All top-level fields shown in that envelope must be present.
            evidenceContextIds may contain only contextId values copied from supplied provenance.
            content may be null only when communicationIntent is NONE.
            If tools are advertised in a future request, follow only the tool-request rules and definitions supplied by SCARLET.
            Return only valid JSON matching SCARLET's LlmResponse protocol, with nothing before or after the JSON object.
            Do not wrap JSON in Markdown or code fences.
            """.strip();

    private final ObjectMapper objectMapper;

    public LlmPromptBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public LlmPrompt build(LlmRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("LLM request must not be null");
        }

        try {
            return new LlmPrompt(SYSTEM_INSTRUCTION, objectMapper.writeValueAsString(request));
        } catch (Exception exception) {
            throw new IllegalArgumentException("Could not serialize LLM request: " + exception.getMessage(), exception);
        }
    }
}
