package xyz.skaerf.scarlet.memory;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class MemoryService {

    private final MemoryRepository memoryRepository;

    public MemoryService(MemoryRepository memoryRepository) {
        this.memoryRepository = memoryRepository;
    }

    public Memory createMemory(MemoryType type, String content, double confidence, double importance, MemorySource source) {
        Memory memory = new Memory(type, content, confidence, importance, source);
        return memoryRepository.save(memory);
    }

    public List<Memory> getAllMemories() {
        return memoryRepository.findAll();
    }

    public Memory getMemory(UUID id) {
        Memory memory = memoryRepository.findById(id).orElse(null);
        if (memory != null) {
            memory.setLastAccessedAt(Instant.now());
            memoryRepository.save(memory);
        }
        return memory;
    }
}
