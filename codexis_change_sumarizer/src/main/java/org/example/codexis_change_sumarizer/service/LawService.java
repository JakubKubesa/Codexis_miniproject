package org.example.codexis_change_sumarizer.service;

import org.example.codexis_change_sumarizer.dto.DiffRequest;
import org.example.codexis_change_sumarizer.model.Law;
import org.example.codexis_change_sumarizer.repository.LawRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LawService {


    private final LawRepository lawRepository;
    private final AiDiffService aiDiffService;

    public LawService(LawRepository lawRepository, AiDiffService aiDiffService) {
        this.lawRepository = lawRepository;
        this.aiDiffService = aiDiffService;
    }

    @Transactional
    public Law addNewVersion(String title, String content, Long originalId) {
        Law newLaw;

        if (originalId != null) {
            // Původní zákon existuje → posuneme jeho stav
            Law originalLaw = lawRepository.findById(originalId)
                    .orElseThrow(() -> new RuntimeException("Original law not found"));

            if (originalLaw.getState() == Law.State.NEW) {
                originalLaw.setState(Law.State.OLD);
                lawRepository.save(originalLaw);
            }



            // Vytvoření nové verze
            newLaw = new Law(title, content, Law.State.NEW, originalId);

            DiffRequest request = new DiffRequest(originalLaw.getContent(), content);
            String summary = aiDiffService.summarizeDiff(request).block().summary();
            newLaw.setDiffSummary(summary);

        } else {
            // úplně nový zákon → state NEW
            newLaw = new Law(title, content, Law.State.NEW, null);
        }

        return lawRepository.save(newLaw);
    }


}
