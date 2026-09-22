package xyz.skaerf.scarlet.llm;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.skaerf.scarlet.reasoning.ReasoningService;

@RestController
public class LlmController {

    private final ReasoningService reasoningService;
    private final LlmPromptBuilder promptBuilder;
    private final LlmService llmService;

    public LlmController(ReasoningService reasoningService, LlmPromptBuilder promptBuilder, LlmService llmService) {
        this.reasoningService = reasoningService;
        this.promptBuilder = promptBuilder;
        this.llmService = llmService;
    }

    @GetMapping("/llm/latest")
    public ResponseEntity<LlmRequest> getLatestRequest() {
        LlmRequest request = reasoningService.getLatestLlmRequest();
        if (request == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(request);
    }

    @GetMapping("/llm/latest/prompt")
    public ResponseEntity<LlmPrompt> getLatestPrompt() {
        LlmRequest request = reasoningService.getLatestLlmRequest();
        if (request == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(promptBuilder.build(request));
    }

    @GetMapping("/llm/latest/response")
    public ResponseEntity<LlmResponse> getLatestResponse() {
        LlmResponse response = llmService.getLatestResponse();
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/llm/latest/raw")
    public ResponseEntity<String> getLatestRawResponse() {
        String rawResponse = llmService.getLatestRawResponse();
        if (rawResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rawResponse);
    }

    @GetMapping("/llm/latest/error")
    public ResponseEntity<LlmFailure> getLatestError() {
        LlmFailure failure = llmService.getLatestFailure();
        if (failure == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(failure);
    }
}
