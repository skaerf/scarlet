package xyz.skaerf.scarlet.memory;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class MemoryController {

    private final MemoryService memoryService;

    public MemoryController(MemoryService memoryService) {
        this.memoryService = memoryService;
    }

    @GetMapping("/memories")
    public List<Memory> getMemories() {
        return memoryService.getAllMemories();
    }

    @GetMapping("/memories/{id}")
    public Memory getMemory(@PathVariable UUID id) {
        return memoryService.getMemory(id);
    }

    @PostMapping("/memories")
    public Memory createMemory(@RequestParam MemoryType type, @RequestParam String content, @RequestParam double confidence, @RequestParam double importance, @RequestParam MemorySource source) {
        return memoryService.createMemory(type, content, confidence, importance, source);
    }
}
