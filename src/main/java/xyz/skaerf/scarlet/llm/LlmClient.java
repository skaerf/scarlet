package xyz.skaerf.scarlet.llm;

import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
public class LlmClient {

    private final LlmProperties properties;
    private final LlmPromptBuilder promptBuilder;
    private final ObjectMapper objectMapper;
    private final RestClient restClient;

    public LlmClient(LlmProperties properties, LlmPromptBuilder promptBuilder, ObjectMapper objectMapper, RestClient.Builder restClientBuilder) {
        this.properties = properties;
        this.promptBuilder = promptBuilder;
        this.objectMapper = objectMapper;
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Duration timeout = properties.getRequestTimeout();
        requestFactory.setConnectTimeout(timeout);
        requestFactory.setReadTimeout(timeout);
        this.restClient = restClientBuilder
                .requestFactory(requestFactory)
                .build();
    }

    public String send(LlmRequest request) {
        if (!properties.hasRequiredConfiguration()) {
            throw new IllegalStateException("LLM base URL and model must be configured");
        }

        LlmPrompt prompt = promptBuilder.build(request);
        Map<String, Object> requestBody = Map.of(
                "model", properties.getModel(),
                "messages", List.of(
                        Map.of("role", "system", "content", prompt.getSystemInstruction()),
                        Map.of("role", "user", "content", prompt.getRequestJson())
                ),
                "response_format", responseFormat(),
                "stream", false
        );

        String responseBody = restClient.post()
                .uri(chatCompletionsUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> {
                    if (properties.getApiKey() != null && !properties.getApiKey().isBlank()) {
                        headers.setBearerAuth(properties.getApiKey());
                    }
                })
                .body(requestBody)
                .retrieve()
                .body(String.class);

        return extractModelOutput(responseBody);
    }

    private Map<String, Object> responseFormat() {
        Map<String, Object> finalResponseSchema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "content", Map.of("type", List.of("string", "null")),
                        "reason", Map.of("type", "string"),
                        "communicationIntent", Map.of(
                                "type", "string",
                                "enum", List.of("NONE", "SPEAK", "TEXT", "SPEAK_AND_TEXT")
                        ),
                        "evidenceContextIds", Map.of(
                                "type", "array",
                                "items", Map.of("type", "string")
                        )
                ),
                "required", List.of("content", "reason", "communicationIntent", "evidenceContextIds"),
                "additionalProperties", false
        );

        Map<String, Object> responseSchema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "responseType", Map.of("type", "string", "const", "FINAL_RESPONSE"),
                        "finalResponse", finalResponseSchema,
                        "toolRequest", Map.of("type", "null")
                ),
                "required", List.of("responseType", "finalResponse", "toolRequest"),
                "additionalProperties", false
        );

        return Map.of(
                "type", "json_schema",
                "json_schema", Map.of(
                        "name", "scarlet_llm_response",
                        "strict", true,
                        "schema", responseSchema
                )
        );
    }

    private String chatCompletionsUrl() {
        String baseUrl = properties.getBaseUrl().strip();
        while (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        return baseUrl + "/chat/completions";
    }

    private String extractModelOutput(String responseBody) {
        if (responseBody == null || responseBody.isBlank()) {
            throw new IllegalArgumentException("LLM endpoint returned an empty response body");
        }

        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode content = root.path("choices").path(0).path("message").path("content");
            if (!content.isString() || content.asString().isBlank()) {
                throw new IllegalArgumentException("LLM endpoint response did not contain choices[0].message.content");
            }
            return content.asString();
        } catch (IllegalArgumentException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new IllegalArgumentException("Could not parse LLM endpoint response: " + exception.getMessage(), exception);
        }
    }
}
