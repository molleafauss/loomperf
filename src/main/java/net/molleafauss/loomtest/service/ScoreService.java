package net.molleafauss.loomtest.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ScoreService {

    private final WebClient client;

    public ScoreService() {
        client = WebClient.create("http://localhost:1080");
    }

    public Score getScore(String name) {
        return client.post()
                .uri("/user/score", name)
                .retrieve()
                .bodyToMono(Score.class)
                .block();
    }
}
