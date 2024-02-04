package com.jipark.auth.entities.game;

import com.jipark.auth.entities.emuns.OauthProviderType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Column;

import java.sql.Timestamp;

@Getter
@Setter
@RequiredArgsConstructor
public class OauthClientToken {
    @Id
    private long id;

    @Column("user_id")
    private final long userId;

    @Column("provider_user_id")
    private final long providerUserId;

    @Column("provider_type")
    private final OauthProviderType providerType;

    @Column("access_token")
    private final String accessToken;

    @Column("refresh_token")
    private final String refreshToken;

    @Column("expires_at")
    private final Timestamp expiresAt;

    @Column("scope")
    private final String scope;

    @Column("issued_at")
    @ReadOnlyProperty
    private Timestamp issuedAt;
}