package com.jipark.auth.dtos.oauth;

public interface OauthGrantResponse {
    String getAccessToken();
    String getRefreshToken();
    int getExpiresIn();
}
