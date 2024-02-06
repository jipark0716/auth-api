package com.jipark.auth.http.controllers.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jipark.auth.http.controllers.BaseController;
import com.jipark.auth.http.request.AuthorizeRequest;
import com.jipark.auth.services.DiscordOauthService;
import com.jipark.auth.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Objects;


@RequiredArgsConstructor
@Component
public class DiscordOauthController extends BaseController {
    private final DiscordOauthService service;
    private final JwtService jwtService;

    public Mono<ServerResponse> redirect(ServerRequest request) {
        return ServerResponse.status(HttpStatus.FOUND)
                .location(service.getAuthorizeUrl(request.uri()))
                .build();
    }

    public Mono<ServerResponse> authorize(ServerRequest request) {
        return request.bind(AuthorizeRequest.class)
                .flatMap(o -> service.Authorize(o.code, request.uri()))
                .map(jwtService::generateJwtToken)
                .flatMap(o -> {
                    try {
                        var tokenString = Base64.getEncoder().encodeToString(new ObjectMapper().writeValueAsBytes(o));
                        var uri = new URI("jipark://authorize?code=" + tokenString);
                        return ServerResponse.status(HttpStatus.FOUND)
                                .location(uri)
                                .build();
                    } catch (JsonProcessingException|URISyntaxException e) {
                        return Mono.error(e);
                    }
                });
    }
}
