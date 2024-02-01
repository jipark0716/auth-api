package com.jipark.auth.services;

import com.jipark.auth.dtos.oauth.DiscordResponse;
import com.jipark.auth.exceptions.oauth.GrantFailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

import org.springframework.http.MediaType;

@Component
public class DiscordOauthService {
    @Value("${oauth.discord.authorize-url}")
    private String authorizeUrl;

    @Value("${oauth.discord.client-secret}")
    private String clientSecret;

    @Value("${oauth.discord.client-id}")
    private String clientId;

    @Value("${oauth.discord.scope}")
    private String scope;
    private final WebClient client;

    public DiscordOauthService(
            @Value("${oauth.discord.grant-url}") String grantUrl,
            @Value("${oauth.discord.basic-auth}") String basicAuth
    ) {
        client = WebClient
                .builder()
                .baseUrl(grantUrl)
                .defaultHeader("Authorization", "Basic " + basicAuth)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public URI getAuthorizeUrl(URI host) {
        return getAuthorizeUrl(host.toString());
    }

    public URI getAuthorizeUrl(String host) {
        return UriComponentsBuilder.fromHttpUrl(authorizeUrl)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", host + "/redirect")
                .queryParam("scope", scope)
                .build()
                .toUri();
    }

    public Mono<DiscordResponse> grantToken(String code, URI redirectUri) {
        return grantToken(code, redirectUri.getScheme() + "://" + redirectUri.getAuthority() + redirectUri.getPath());
    }

    public Mono<DiscordResponse> grantToken(String code, String redirectUri)
    {
        BodyInserters.FormInserter<String> request = BodyInserters.fromFormData("grant_type", "authorization_code")
                .with("code", code)
                .with("redirect_uri", redirectUri);

        return client.post()
                .contentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .body(request)
                .retrieve()
                .bodyToMono(DiscordResponse.class)
                .onErrorMap(o -> {
                    if (o instanceof WebClientResponseException e) {
                        return new GrantFailException(e.getResponseBodyAsString(), e);
                    }
                    return new GrantFailException("fail discord grant", o);
                });
    }
}