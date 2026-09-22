package xyz.skaerf.scarlet.memory;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Memory>> getMemories(@RequestParam(defaultValue = "20") int limit) {
        try {
            return ResponseEntity.ok(memoryService.getAllMemories(limit));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/memories/search")
    public ResponseEntity<List<Memory>> searchMemories(@RequestParam String q, @RequestParam(defaultValue = "20") int limit) {
        try {
            return ResponseEntity.ok(memoryService.searchMemories(q, limit));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/memories/type/{type}")
    public ResponseEntity<List<Memory>> getMemoriesByType(@PathVariable MemoryType type, @RequestParam(defaultValue = "20") int limit) {
        try {
            return ResponseEntity.ok(memoryService.getMemoriesByType(type, limit));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/memories/important")
    public ResponseEntity<List<Memory>> getImportantMemories(@RequestParam double minImportance, @RequestParam(defaultValue = "20") int limit) {
        try {
            return ResponseEntity.ok(memoryService.getImportantMemories(minImportance, limit));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/memories/{id}")
    public ResponseEntity<Memory> getMemory(@PathVariable UUID id) {
        Memory memory = memoryService.getMemory(id);
        if (memory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(memory);
    }

    @PostMapping("/memories")
    public ResponseEntity<Memory> createMemory(@RequestParam MemoryType type, @RequestParam String content, @RequestParam double confidence, @RequestParam double importance, @RequestParam MemorySource source) {
        try {
            return ResponseEntity.ok(memoryService.createMemory(type, content, confidence, importance, source));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/memories/{id}")
    public ResponseEntity<Memory> updateMemory(@PathVariable UUID id, @RequestParam MemoryType type, @RequestParam String content, @RequestParam double confidence, @RequestParam double importance, @RequestParam MemorySource source) {
        try {
            Memory memory = memoryService.updateMemory(id, type, content, confidence, importance, source);
            if (memory == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(memory);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/memories/{id}")
    public ResponseEntity<Void> deleteMemory(@PathVariable UUID id) {
        if (!memoryService.deleteMemory(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
