package com.jipark.auth;

import com.jipark.auth.controllers.oauth.DiscordOauthController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class Router {
    public final DiscordOauthController discordOauthController;

    @Bean
    public RouterFunction<ServerResponse> route() {

        return RouterFunctions
                .route(GET("/oauth/discord").and(accept(MediaType.ALL)), discordOauthController::redirect)
                .andRoute(GET("/oauth/discord/redirect").and(accept(MediaType.ALL)), discordOauthController::authorize);
    }
}