package org.example.codexis_change_sumarizer.repository;

import org.example.codexis_change_sumarizer.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
