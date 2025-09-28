package org.example.codexis_change_sumarizer.repository;

import java.util.List;
import org.example.codexis_change_sumarizer.model.Law;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawRepository extends JpaRepository<Law, Long> {
    List<Law> findByOriginalIdAndState(Long originalId, Law.State state);
    List<Law> findByState(Law.State state);
}