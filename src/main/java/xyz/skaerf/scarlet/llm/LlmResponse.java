package xyz.skaerf.scarlet.llm;

public class LlmResponse {

    private LlmResponseType responseType;
    private FinalResponse finalResponse;
    private ToolRequest toolRequest;

    public LlmResponse() {
    }

    public LlmResponse(FinalResponse finalResponse) {
        this.responseType = LlmResponseType.FINAL_RESPONSE;
        this.finalResponse = finalResponse;
        this.toolRequest = null;
    }

    public LlmResponse(ToolRequest toolRequest) {
        this.responseType = LlmResponseType.TOOL_REQUEST;
        this.finalResponse = null;
        this.toolRequest = toolRequest;
    }

    public LlmResponseType getResponseType() {
        return this.responseType;
    }

    public void setResponseType(LlmResponseType responseType) {
        this.responseType = responseType;
    }

    public FinalResponse getFinalResponse() {
        return this.finalResponse;
    }

    public void setFinalResponse(FinalResponse finalResponse) {
        this.finalResponse = finalResponse;
    }

    public ToolRequest getToolRequest() {
        return this.toolRequest;
    }

    public void setToolRequest(ToolRequest toolRequest) {
        this.toolRequest = toolRequest;
    }
}
