package com.jipark.auth.repositories.game;

import com.jipark.auth.entities.game.OauthClientToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor()
@Repository
public class OauthClientTokenRepository {
    private final DatabaseClient client;
    private final OauthClientTokenCrudRepository crudRepository;

    public Mono<OauthClientToken> save(OauthClientToken e) {
        return crudRepository.save(e);
    }

    public Mono<Long> saveByUserId(OauthClientToken token) {
        var query = """
UPDATE `game`.`oauth_client_token` SET
    `access_token` = :access_token,
    `refresh_token` = :refresh_token,
    `expires_at` = :expires_at,
    `scope` = :scope
WHERE
    `user_id` = :user_id AND
    `provider_type` = :provider_type""";

        return client.sql(query)
                .bindValues(token.toColumMap(query))
                .fetch()
                .rowsUpdated();
    }

    public Mono<Long> saveByProviderUserId(OauthClientToken token) {
        var query = """
UPDATE `game`.`oauth_client_token` SET
    `access_token` = :access_token,
    `refresh_token` = :refresh_token,
    `expires_at` = :expires_at,
    `scope` = :scope
WHERE
    `provider_user_id` = :user_id AND
    `provider_type` = :provider_type""";

        return client.sql(query)
                .bindValues(token.toColumMap(query))
                .fetch()
                .rowsUpdated();
    }
}

@Repository
interface OauthClientTokenCrudRepository extends ReactiveCrudRepository<OauthClientToken, Long> {
}
