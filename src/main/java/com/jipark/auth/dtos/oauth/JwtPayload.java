package com.jipark.auth.dtos.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtPayload {
    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("id")
    private long id;
}
