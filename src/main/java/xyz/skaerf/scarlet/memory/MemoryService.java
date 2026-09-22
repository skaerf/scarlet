package xyz.skaerf.scarlet.memory;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public List<Memory> getAllMemories(int limit) {
        return memoryRepository.findAllByOrderByImportanceDescUpdatedAtDesc(pageRequest(limit));
    }

    public List<Memory> searchMemories(String query, int limit) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("Search query must not be blank");
        }
        return memoryRepository.findByContentContainingIgnoreCaseOrderByImportanceDescUpdatedAtDesc(query, pageRequest(limit));
    }

    public List<Memory> getMemoriesByType(MemoryType type, int limit) {
        return memoryRepository.findByTypeOrderByImportanceDescUpdatedAtDesc(type, pageRequest(limit));
    }

    public List<Memory> getImportantMemories(double minimumImportance, int limit) {
        if (!Double.isFinite(minimumImportance) || minimumImportance < 0.0 || minimumImportance > 1.0) {
            throw new IllegalArgumentException("Minimum importance must be between 0.0 and 1.0");
        }
        return memoryRepository.findByImportanceGreaterThanEqualOrderByImportanceDescUpdatedAtDesc(minimumImportance, pageRequest(limit));
    }

    public List<Memory> getMemoriesForContext(String activeApplication, int limit) {
        if (limit < 1 || limit > 10) {
            throw new IllegalArgumentException("Context memory limit must be between 1 and 10");
        }

        List<Memory> memories = new ArrayList<>();
        Set<UUID> memoryIds = new HashSet<>();

        if (activeApplication != null) {
            String applicationName = activeApplication.trim();
            if (!applicationName.isEmpty() && !applicationName.equalsIgnoreCase("unknown")) {
                List<Memory> matches = searchMemories(applicationName, Math.min(5, limit));
                for (Memory memory : matches) {
                    if (memoryIds.add(memory.getId())) {
                        memories.add(memory);
                    }
                }
            }
        }

        for (Memory memory : getAllMemories(limit)) {
            if (memories.size() >= limit) {
                break;
            }
            if (memoryIds.add(memory.getId())) {
                memories.add(memory);
            }
        }

        return memories;
    }

    public Memory getMemory(UUID id) {
        Memory memory = memoryRepository.findById(id).orElse(null);
        if (memory != null) {
            memory.setLastAccessedAt(Instant.now());
            memoryRepository.save(memory);
        }
        return memory;
    }

    public Memory updateMemory(UUID id, MemoryType type, String content, double confidence, double importance, MemorySource source) {
        Memory memory = memoryRepository.findById(id).orElse(null);
        if (memory == null) {
            return null;
        }

        memory.update(type, content, confidence, importance, source);
        return memoryRepository.save(memory);
    }

    public boolean deleteMemory(UUID id) {
        if (!memoryRepository.existsById(id)) {
            return false;
        }

        memoryRepository.deleteById(id);
        return true;
    }

    private PageRequest pageRequest(int limit) {
        if (limit < 1 || limit > 100) {
            throw new IllegalArgumentException("Limit must be between 1 and 100");
        }
        return PageRequest.of(0, limit);
    }
}
