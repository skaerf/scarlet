package xyz.skaerf.scarlet.llm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LlmService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LlmService.class);

    private final LlmProperties properties;
    private final LlmClient client;
    private final LlmResponseParser responseParser;
    private LlmResponse latestResponse;
    private String latestRawResponse;
    private LlmFailure latestFailure;

    public LlmService(LlmProperties properties, LlmClient client, LlmResponseParser responseParser) {
        this.properties = properties;
        this.client = client;
        this.responseParser = responseParser;
    }

    public void process(LlmRequest request) {
        if (!properties.isEnabled()) {
            return;
        }
        if (!properties.hasRequiredConfiguration()) {
            recordFailure(request, "CONFIGURATION", "LLM integration is enabled, but base URL or model is not configured", null);
            return;
        }

        String rawResponse;
        try {
            rawResponse = client.send(request);
        } catch (Exception exception) {
            recordFailure(request, "HTTP", exception.getMessage(), exception);
            return;
        }
        setLatestRawResponse(rawResponse);

        try {
            LlmResponse response = responseParser.parse(rawResponse, request);
            setLatestResponse(response);
        } catch (Exception exception) {
            recordFailure(request, "PARSING_OR_VALIDATION", exception.getMessage(), exception);
        }
    }

    private void recordFailure(LlmRequest request, String stage, String message, Exception exception) {
        String safeMessage = message == null || message.isBlank() ? "Unknown LLM failure" : message;
        LlmFailure failure = new LlmFailure(request.getRequestId(), Instant.now(), stage, safeMessage);
        synchronized (this) {
            this.latestFailure = failure;
        }
        if (exception == null) {
            LOGGER.error("LLM request {} failed during {}: {}", request.getRequestId(), stage, safeMessage);
        } else {
            LOGGER.error("LLM request {} failed during {}: {}", request.getRequestId(), stage, safeMessage, exception);
        }
    }

    private synchronized void setLatestResponse(LlmResponse response) {
        this.latestResponse = response;
    }

    private synchronized void setLatestRawResponse(String rawResponse) {
        this.latestRawResponse = rawResponse;
    }

    public synchronized LlmResponse getLatestResponse() {
        return this.latestResponse;
    }

    public synchronized String getLatestRawResponse() {
        return this.latestRawResponse;
    }

    public synchronized LlmFailure getLatestFailure() {
        return this.latestFailure;
    }
}
