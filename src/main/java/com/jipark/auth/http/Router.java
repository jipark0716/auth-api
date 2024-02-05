package com.jipark.auth.http;

import com.jipark.auth.http.controllers.JwtController;
import com.jipark.auth.http.controllers.UserController;
import com.jipark.auth.http.controllers.oauth.DiscordOauthController;
import com.jipark.auth.http.filters.JwtFilter;
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
    private final UserController userController;
    private final DiscordOauthController discordOauthController;
    private final JwtController jwtController;
    private final JwtFilter jwtFilter;

    @Bean
    public RouterFunction<ServerResponse> route() {

        return RouterFunctions
                .route(GET("/oauth/discord").and(accept(MediaType.ALL)), discordOauthController::redirect)
                .andRoute(GET("/oauth/discord/redirect").and(accept(MediaType.ALL)), discordOauthController::authorize);
    }

    @Bean
    public RouterFunction<ServerResponse> routeWithAuth() {
        return RouterFunctions
                .route(GET("/api/users/{user_id}").and(accept(MediaType.ALL)), userController::find)
                .andRoute(GET("/api/users").and(accept(MediaType.ALL)), userController::findMany)
                .filter(jwtFilter.handle("access"));
    }

    @Bean
    public RouterFunction<ServerResponse> routeWithRefreshToken() {
        return RouterFunctions
                .route(GET("/token/refresh").and(accept(MediaType.ALL)), jwtController::refresh)
                .filter(jwtFilter.handle("refresh"));
    }
}