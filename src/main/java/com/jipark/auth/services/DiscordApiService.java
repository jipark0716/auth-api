package com.jipark.auth.services;

import com.jipark.auth.dtos.webclient.discod.resources.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DiscordApiService {
    private final WebClient client;

    public Mono<User> getUser()
    {
        return client.get()
                .uri("/users/@me")
                .retrieve()
                .bodyToMono(User.class);
    }
}