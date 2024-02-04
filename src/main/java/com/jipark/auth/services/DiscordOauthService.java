package com.jipark.auth.services;

import com.jipark.auth.dtos.oauth.DiscordResponse;
import com.jipark.auth.entities.emuns.OauthProviderType;
import com.jipark.auth.entities.game.User;
import com.jipark.auth.exceptions.oauth.GrantFailException;
import com.jipark.auth.factories.DiscordApiClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@Component
public class DiscordOauthService extends BaseOauthService {
    @Autowired
    private DiscordApiClientFactory discordApiClientFactory;

    @Value("${discord.authorize-url}")
    private String authorizeUrl;

    @Value("${discord.client-secret}")
    private String clientSecret;

    @Value("${discord.client-id}")
    private String clientId;

    private final WebClient client;

    public DiscordOauthService(
            @Value("${discord.scope}") String scope,
            @Value("${discord.grant-url}") String grantUrl,
            @Value("${discord.basic-auth}") String basicAuth
    ) {
        super(OauthProviderType.discord, scope);
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

    public Mono<User> Authorize(String code, URI redirectUri) {
        return grantToken(code, redirectUri.getScheme() + "://" + redirectUri.getAuthority() + redirectUri.getPath())
                .checkpoint("success discord")
                .flatMap(o ->
                    discordApiClientFactory.create(o.getAccessToken())
                        .getUser()
                        .zipWith(Mono.just(o))
                )
                .flatMap(o -> createOrUpdate(o.getT1(), o.getT2()));
    }

    private URI getAuthorizeUrl(String host) {
        return UriComponentsBuilder.fromHttpUrl(authorizeUrl)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", host + "/redirect")
                .queryParam("scope", scope)
                .build()
                .toUri();
    }

    private Mono<DiscordResponse> grantToken(String code, String redirectUri)
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