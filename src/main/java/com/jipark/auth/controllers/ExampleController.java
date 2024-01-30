package com.jipark.auth.controllers;

import com.jipark.auth.dtos.response.Response;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ExampleController {
    public Mono<ServerResponse> hello(ServerRequest request) {
        Response res = new Response("asdf");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(res));
    }
}
