package com.jipark.auth.repositories.game;

import com.jipark.auth.entities.emuns.OauthProviderType;
import com.jipark.auth.entities.game.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    @Query("""
SELECT 
    *
FROM
    `user` `u`
WHERE EXISTS(
    SELECT `id` FROM `oauth_client_token` `t`
    WHERE
        `t`.`provider_user_id` = :user_id and
        `t`.`provider_type` = :type AND
        `u`.`id` = `t`.`user_id`
)
""")
    Mono<User> findByProviderUserId(@Param("user_id") long userId, @Param("type") OauthProviderType type);
}
