package com.jipark.auth.services;

import com.jipark.auth.dtos.oauth.IUser;
import com.jipark.auth.dtos.oauth.OauthGrantResponse;
import com.jipark.auth.entities.emuns.OauthProviderType;
import com.jipark.auth.entities.game.OauthClientToken;
import com.jipark.auth.entities.game.User;
import com.jipark.auth.repositories.game.OauthClientTokenRepository;
import com.jipark.auth.repositories.game.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.sql.Timestamp;

@RequiredArgsConstructor()
public abstract class BaseOauthService {
    @Autowired
    protected OauthClientTokenRepository oauthClientTokenRepository;
    @Autowired
    private UserRepository userRepository;

    private final OauthProviderType providerType;
    protected final String scope;

    protected Mono<User> createOrUpdate(IUser providerUser, OauthGrantResponse token) {
        return userRepository.findByProviderUserId(providerUser.getId(), providerType)
                .doOnSuccess(o -> updateToken(providerUser, o, token))
                .switchIfEmpty(Mono.defer(() -> createToken(providerUser, token)));
    }

    protected void updateToken(IUser providerUser, User user, OauthGrantResponse token) {
        if (user == null) return;
        oauthClientTokenRepository.saveByUserId(makeToken(providerUser, user, token))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }

    protected Mono<User> createToken(IUser providerUser, OauthGrantResponse token) {
        return userRepository.save(makeUser(providerUser))
                .doOnSuccess(o ->
                        oauthClientTokenRepository.save(makeToken(providerUser, o, token))
                                .subscribeOn(Schedulers.boundedElastic())
                                .subscribe());
    }

    protected User makeUser(IUser user) {
        return new User(user.getUsername(), user.getAvatar());
    }

    protected OauthClientToken makeToken(IUser providerUser, User user, OauthGrantResponse token) {
        return new OauthClientToken(
                user.getId(),
                providerUser.getId(),
                providerType,
                token.getAccessToken(),
                token.getRefreshToken(),
                new Timestamp(System.currentTimeMillis() + (1000L * token.getExpiresIn())),
                scope
        );
    }
}