package com.jipark.auth.http.controllers;

import com.jipark.auth.dtos.oauth.JwtPayload;
import com.jipark.auth.exceptions.oauth.UnauthorizedException;
import com.jipark.auth.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtController extends BaseController {
    private final JwtService jwtService;
    public Mono<ServerResponse> refresh(ServerRequest request) {
        var optional = request.attribute(JwtPayload.class.getName());
        if (optional.isEmpty()) {
            return Mono.error(new UnauthorizedException("Authorization"));
        }
        JwtPayload jwt = (JwtPayload) optional.get();
        return ok(jwtService.generateJwtToken(jwt.getId()));
    }
}