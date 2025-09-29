package org.example.codexis_change_sumarizer.service;

import org.example.codexis_change_sumarizer.dto.DiffRequest;
import org.example.codexis_change_sumarizer.dto.DiffResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class AiDiffService {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String apiKey;

    public AiDiffService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
    }

    public Mono<DiffResponse> summarizeDiff(DiffRequest request) {
        String prompt = """
                Porovnej následující dvě formulace a stručně shrň hlavní rozdíly v češtině:

                Původní formulace:
                %s

                Nová formulace:
                %s
                """.formatted(request.oldLaw(), request.newLaw());

        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(Map.of(
                        "model", "gpt-4o-mini",
                        "messages", new Object[]{
                                Map.of("role", "system", "content",
                                        "Jsi asistent, který shrnuje rozdíly mezi dvěma krátkými větami nebo formulacemi v češtině, stručně a jasně."),
                                Map.of("role", "user", "content", prompt)
                        }
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(resp -> {
                    var choices = (java.util.List<Map<String, Object>>) resp.get("choices");
                    var first = (Map<String, Object>) choices.get(0).get("message");
                    return new DiffResponse(first.get("content").toString());
                });
    }

    public Mono<String> simpleCheck(String prompt) {
        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(Map.of(
                        "model", "gpt-4o-mini",
                        "messages", new Object[]{
                                Map.of("role", "system", "content",
                                        "Odpovídej pouze ANO nebo NE."),
                                Map.of("role", "user", "content", prompt)
                        }
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(resp -> {
                    var choices = (List<Map<String, Object>>) resp.get("choices");
                    var first = (Map<String, Object>) choices.get(0).get("message");
                    return first.get("content").toString();
                });
    }

}
