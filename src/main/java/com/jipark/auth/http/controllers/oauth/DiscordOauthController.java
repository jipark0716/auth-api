package com.jipark.auth.http.controllers.oauth;

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
                .flatMap(o -> ok(jwtService.generateJwtToken(o)));
    }
}
