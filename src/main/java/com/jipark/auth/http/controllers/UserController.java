package com.jipark.auth.http.controllers;

import com.jipark.auth.http.request.FindByIdRequest;
import com.jipark.auth.http.response.CollectionResponse;
import com.jipark.auth.repositories.game.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UserRepository userRepository;

    public Mono<ServerResponse> find(ServerRequest request) {
        var userId = Long.parseLong(request.pathVariable("user_id"));
        return userRepository.findById(userId).flatMap(this::ok);
    }

    public Mono<ServerResponse> findMany(ServerRequest request) {
        return request.bind(FindByIdRequest.class)
                .flatMapMany(o -> userRepository.findAllById(List.of(o.getIds())))
                .collectMap(o -> o)
                .map(o -> new CollectionResponse(o.values().toArray().toList()))
                .flatMap(this::ok);
    }
}