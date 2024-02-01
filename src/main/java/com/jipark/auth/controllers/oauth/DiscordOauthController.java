package com.jipark.auth.controllers.oauth;

import com.jipark.auth.controllers.BaseController;
import com.jipark.auth.dtos.request.oauth.discord.AuthorizeRequest;
import com.jipark.auth.services.DiscordOauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class DiscordOauthController extends BaseController {

    private final DiscordOauthService service;

    public Mono<ServerResponse> redirect(ServerRequest request) {
        return ServerResponse.status(HttpStatus.FOUND)
                .location(service.getAuthorizeUrl(request.uri()))
                .build();
    }

    public Mono<ServerResponse> authorize(ServerRequest request) {
        return request.bind(AuthorizeRequest.class)
                .flatMap(o -> service.grantToken(o.code, request.uri()))
                .flatMap(o -> ok(o));
    }
}
