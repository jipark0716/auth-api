package com.jipark.auth.http.controllers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class JwtController extends BaseController {
    public Mono<ServerResponse> refresh(ServerRequest request) {
        return ok("sdf");
    }
}
