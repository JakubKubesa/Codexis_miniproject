package org.example.codexis_change_sumarizer.controller;

import org.example.codexis_change_sumarizer.model.LawCategoryMatch;
import org.example.codexis_change_sumarizer.repository.LawCategoryMatchRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final LawCategoryMatchRepository matchRepository;

    public NotificationController(LawCategoryMatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @GetMapping
    public List<LawCategoryMatch> getAllRelevant() {
        return matchRepository.findAll()
                .stream()
                .filter(LawCategoryMatch::isRelevant)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Long id) {
        matchRepository.deleteById(id);
    }
}
