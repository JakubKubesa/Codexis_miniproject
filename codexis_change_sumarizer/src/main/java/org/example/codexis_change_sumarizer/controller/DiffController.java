package org.example.codexis_change_sumarizer.controller;

import org.example.codexis_change_sumarizer.dto.DiffRequest;
import org.example.codexis_change_sumarizer.dto.DiffResponse;
import org.example.codexis_change_sumarizer.service.AiDiffService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api")
public class DiffController {

    private final AiDiffService aiDiffService;

    public DiffController(AiDiffService aiDiffService) {
        this.aiDiffService = aiDiffService;
    }


    @PostMapping("/diff")
    public Mono<DiffResponse> summarizeChanges(@RequestBody DiffRequest request) {
        return aiDiffService.summarizeDiff(request);
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
