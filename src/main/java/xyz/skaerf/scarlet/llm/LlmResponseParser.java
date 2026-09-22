package xyz.skaerf.scarlet.llm;

import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class LlmResponseParser {

    private final ObjectMapper objectMapper;
    private final LlmResponseValidator validator;

    public LlmResponseParser(ObjectMapper objectMapper, LlmResponseValidator validator) {
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    public LlmResponse parse(String json, LlmRequest request) {
        if (json == null || json.isBlank()) {
            throw new IllegalArgumentException("LLM response JSON must not be blank");
        }

        LlmResponse response;
        try {
            response = objectMapper.readValue(json, LlmResponse.class);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Invalid LLM response JSON: " + exception.getMessage(), exception);
        }

        validator.validate(response, request);
        return response;
    }
}
