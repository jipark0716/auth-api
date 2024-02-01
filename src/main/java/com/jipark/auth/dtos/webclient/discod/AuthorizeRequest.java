package com.jipark.auth.dtos.webclient.discod;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorizeRequest {
    @NotEmpty
    public String code;
}
