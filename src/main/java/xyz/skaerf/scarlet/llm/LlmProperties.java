package xyz.skaerf.scarlet.llm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "scarlet.llm")
public class LlmProperties {

    private boolean enabled;
    private String baseUrl;
    private String model;
    private String apiKey;
    private Duration requestTimeout = Duration.ofSeconds(30);

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Duration getRequestTimeout() {
        return this.requestTimeout;
    }

    public void setRequestTimeout(Duration requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public boolean hasRequiredConfiguration() {
        return this.baseUrl != null && !this.baseUrl.isBlank()
                && this.model != null && !this.model.isBlank();
    }
}
