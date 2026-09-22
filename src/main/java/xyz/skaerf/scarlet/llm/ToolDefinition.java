package xyz.skaerf.scarlet.llm;

import java.util.LinkedHashMap;
import java.util.Map;

public class ToolDefinition {

    private String name;
    private String description;
    private Map<String, Object> argumentSchema;

    public ToolDefinition() {
        this.argumentSchema = new LinkedHashMap<>();
    }

    public ToolDefinition(String name, String description, Map<String, Object> argumentSchema) {
        this.name = name;
        this.description = description;
        setArgumentSchema(argumentSchema);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getArgumentSchema() {
        return this.argumentSchema;
    }

    public void setArgumentSchema(Map<String, Object> argumentSchema) {
        this.argumentSchema = argumentSchema == null ? new LinkedHashMap<>() : new LinkedHashMap<>(argumentSchema);
    }
}
