package xyz.skaerf.scarlet.decision;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DecisionRepository extends JpaRepository<Decision, UUID> {

}
