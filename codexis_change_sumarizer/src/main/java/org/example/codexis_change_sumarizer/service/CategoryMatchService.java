package org.example.codexis_change_sumarizer.service;


import org.example.codexis_change_sumarizer.model.Category;
import org.example.codexis_change_sumarizer.model.Law;
import org.example.codexis_change_sumarizer.model.LawCategoryMatch;
import org.example.codexis_change_sumarizer.repository.CategoryRepository;
import org.example.codexis_change_sumarizer.repository.LawCategoryMatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryMatchService {

    private final AiDiffService aiDiffService;
    private final CategoryRepository categoryRepository;
    private final LawCategoryMatchRepository matchRepository;

    public CategoryMatchService(
            AiDiffService aiDiffService,
            CategoryRepository categoryRepository,
            LawCategoryMatchRepository matchRepository
    ) {
        this.aiDiffService = aiDiffService;
        this.categoryRepository = categoryRepository;
        this.matchRepository = matchRepository;
    }

    public void analyzeLawCategories(Law law) {
        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            String prompt = """
                Zjisti, zda tento zákon souvisí s kategorií "%s".
                Zákon:
                %s

                Odpověz pouze ANO nebo NE.
            """.formatted(category.getName(), law.getContent());

            aiDiffService.simpleCheck(prompt).subscribe(response -> {
                boolean relevant = response.trim().equalsIgnoreCase("ANO");

                LawCategoryMatch match = new LawCategoryMatch();
                match.setLaw(law);
                match.setCategory(category);
                match.setRelevant(relevant);

                matchRepository.save(match);

                if (relevant) {
                    // Zatím jen log, později notifikace
                    System.out.println("📢 Nový zákon spadá do kategorie: " + category.getName());
                }
            });
        }
    }
}
