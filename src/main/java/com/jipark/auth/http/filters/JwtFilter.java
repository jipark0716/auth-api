package com.jipark.auth.http.filters;

import com.jipark.auth.dtos.oauth.JwtPayload;
import com.jipark.auth.exceptions.oauth.UnauthorizedException;
import com.jipark.auth.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.security.InvalidParameterException;

@Component
@RequiredArgsConstructor
public class JwtFilter {
    private final JwtService jwtService;
    private final Class<JwtPayload> payloadType = JwtPayload.class;

    public HandlerFilterFunction<ServerResponse, ServerResponse> handle(String tokenType) {
        return (r, n) -> handle(r, n, tokenType);
    }

    public Mono<ServerResponse> handle(ServerRequest request, HandlerFunction<ServerResponse> next, String tokenType) {
        String token;
        try {
            token = getAuthorization(request);
        } catch (InvalidParameterException e) {
            return createUnauthorizedResponse("missing Authorization header");
        }

        var payload = jwtService.read(token, payloadType);
        if (payload == null || !payload.getTokenType().equals(tokenType)) {
            return createUnauthorizedResponse("Unauthorized");
        }
        request.attributes().put(payloadType.getName(), payload);
        return next.handle(request);
    }

    private String getAuthorization(ServerRequest request) {
        var authorization = request.headers().firstHeader("Authorization");
        if (authorization == null) throw new InvalidParameterException();
        var splitAuthorization = authorization.split(" ");
        if (splitAuthorization.length < 2 || !splitAuthorization[0].equals("Bearer")) {
            throw new InvalidParameterException();
        }
        return splitAuthorization[1];
    }

    private Mono<ServerResponse> createUnauthorizedResponse(String message) {
        return Mono.error(new UnauthorizedException(message));
    }
}
