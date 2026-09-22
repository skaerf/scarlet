package xyz.skaerf.scarlet.memory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface MemoryRepository extends JpaRepository<Memory, UUID> {
    List<Memory> findAllByOrderByImportanceDescUpdatedAtDesc(Pageable pageable);

    List<Memory> findByContentContainingIgnoreCaseOrderByImportanceDescUpdatedAtDesc(String content, Pageable pageable);

    List<Memory> findByTypeOrderByImportanceDescUpdatedAtDesc(MemoryType type, Pageable pageable);

    List<Memory> findByImportanceGreaterThanEqualOrderByImportanceDescUpdatedAtDesc(double minimumImportance, Pageable pageable);
}
