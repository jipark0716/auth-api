package com.jipark.auth.dtos.request.oauth.discord;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorizeRequest {
    @NotEmpty
    public String code;
}
