package org.example.codexis_change_sumarizer.controller;

import org.example.codexis_change_sumarizer.dto.DiffRequest;
import org.example.codexis_change_sumarizer.dto.DiffResponse;
import org.example.codexis_change_sumarizer.model.Law;
import org.example.codexis_change_sumarizer.repository.LawRepository;
import org.example.codexis_change_sumarizer.service.AiDiffService;
import org.example.codexis_change_sumarizer.service.CategoryMatchService;
import org.example.codexis_change_sumarizer.service.LawService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/laws")
public class LawController {

    private final LawRepository lawRepository;
    private final LawService lawService;
    private final AiDiffService aiDiffService;

    private final CategoryMatchService categoryMatchService;

    public LawController(LawRepository lawRepository,LawService lawService,AiDiffService aiDiffService, CategoryMatchService categoryMatchService
    ) {
        this.lawRepository = lawRepository;
        this.lawService = lawService;
        this.aiDiffService = aiDiffService;
        this.categoryMatchService = categoryMatchService;
    }



    //ai response
    @PostMapping("/{id}/diff")
    public Mono<DiffResponse> diffWithOriginal(@PathVariable Long id) {
        Law law = lawRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Law not found"));

        if (law.getOriginalId() == null) {
            return Mono.just(new DiffResponse("Tento zákon nemá žádnou předchozí verzi."));
        }

        Law originalLaw = lawRepository.findById(law.getOriginalId())
                .orElseThrow(() -> new RuntimeException("Original law not found"));

        DiffRequest request = new DiffRequest(originalLaw.getContent(), law.getContent());
        return aiDiffService.summarizeDiff(request);
    }
    // view

    @GetMapping
    public List<Law> getAllLaws() {
        return lawRepository.findAll();
    }


    @GetMapping("/{id}")
    public Law getLaw(@PathVariable Long id) {
        return lawRepository.findById(id).orElseThrow();
    }

    //add + upd + del
    @PostMapping
    public ResponseEntity<?> createLaw(@RequestBody Law law) {
        if (law.getOriginalId() == null) {
            law.setState(Law.State.NEW);
        }
        Law savedLaw = lawRepository.save(law);

        categoryMatchService.analyzeLawCategories(savedLaw);

        Map<String, Object> response = new HashMap<>();
        response.put("law", savedLaw);
        response.put("matchedCategories", List.of());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/version")
    public ResponseEntity<?> createNewVersion(@RequestBody Law law) {
        Law savedLaw = lawService.addNewVersion(law.getTitle(), law.getContent(), law.getOriginalId());

        categoryMatchService.analyzeLawCategories(savedLaw);

        Map<String, Object> response = new HashMap<>();
        response.put("law", savedLaw);
        response.put("matchedCategories", List.of());

        return ResponseEntity.ok(response);
    }



    @PutMapping("/{id}")
    public Law updateLaw(@PathVariable Long id, @RequestBody Law law) {
        Law existing = lawRepository.findById(id).orElseThrow();
        existing.setTitle(law.getTitle());
        existing.setContent(law.getContent());
        return lawRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteLaw(@PathVariable Long id) {
        lawRepository.deleteById(id);
    }
}
