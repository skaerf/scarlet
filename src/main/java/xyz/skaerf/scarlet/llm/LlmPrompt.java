package xyz.skaerf.scarlet.llm;

public class LlmPrompt {

    private String systemInstruction;
    private String requestJson;

    public LlmPrompt() {
    }

    public LlmPrompt(String systemInstruction, String requestJson) {
        this.systemInstruction = systemInstruction;
        this.requestJson = requestJson;
    }

    public String getSystemInstruction() {
        return this.systemInstruction;
    }

    public void setSystemInstruction(String systemInstruction) {
        this.systemInstruction = systemInstruction;
    }

    public String getRequestJson() {
        return this.requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }
}
