package com.jipark.auth.dtos.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscordResponse {
    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("token_type")
    public String tokenType;

    @JsonProperty("expires_in")
    public String expiresIn;

    @JsonProperty("refresh_token")
    public String refreshToken;

    @JsonProperty("scope")
    public String scope;
}