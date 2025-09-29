package org.example.codexis_change_sumarizer.repository;

import org.example.codexis_change_sumarizer.model.LawCategoryMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LawCategoryMatchRepository extends JpaRepository<LawCategoryMatch, Long> {
    List<LawCategoryMatch> findByLawId(Long lawId);
    List<LawCategoryMatch> findByCategoryIdAndRelevantTrue(Long categoryId);
}
