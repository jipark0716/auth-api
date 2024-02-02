package com.jipark.auth.entities.game;

import com.jipark.auth.entities.emuns.OauthProviderType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class OauthClientToken {
    private int id;
    private int userId;
    private OauthProviderType ProviderType;
    private String accessToken;
    private String refreshToken;
    private Time expiresAt;
    private String scope;
    private Time issuedAt;
}