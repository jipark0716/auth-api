package com.jipark.auth.factories;

import com.jipark.auth.services.DiscordApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DiscordApiClientFactory {
    private final WebClient.Builder clientBuilder;

    public DiscordApiClientFactory(@Value("${discord.base-url}") String baseUrl)
    {
        clientBuilder = WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    }

    public DiscordApiService create(String token) {
        return new DiscordApiService(clientBuilder.clone()
                .defaultHeader("Authorization", "Bearer " + token)
                .build());
    }
}
