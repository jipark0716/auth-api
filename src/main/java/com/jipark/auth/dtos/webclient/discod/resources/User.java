package com.jipark.auth.dtos.webclient.discod.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jipark.auth.dtos.oauth.IUser;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements IUser {
    @JsonProperty("id")
    @NotNull
    private long id;

    @JsonProperty("username")
    @NotNull
    private String username;

    @JsonProperty("discriminator")
    @NotNull
    private String discriminator;

    @JsonProperty("global_name")
    private String globalName;

    @JsonProperty("bot")
    private boolean bot;

    @JsonProperty("system")
    private boolean system;

    @JsonProperty("mfa_enabled")
    private boolean mfaEnabled;

    @JsonProperty("banner")
    private String banner;

    @JsonProperty("accent_color")
    private int accentColor;

    @JsonProperty("locale")
    private String locale;

    @JsonProperty("verified")
    private boolean verified;

    @JsonProperty("email")
    private String email;

    @JsonProperty("flags")
    private int flags;

    @JsonProperty("premium_type")
    private int premiumType;

    @JsonProperty("public_flags")
    private int publicFlags;

    @JsonProperty("avatar_decoration_data")
    private String avatarDecorationData;
}
