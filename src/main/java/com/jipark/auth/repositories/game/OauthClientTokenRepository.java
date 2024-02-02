package com.jipark.auth.repositories.game;

import com.jipark.auth.dtos.oauth.DiscordResponse;
import com.jipark.auth.entities.game.OauthClientToken;
import com.jipark.auth.mappers.game.OauthClientTokenMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OauthClientTokenRepository {
    private final OauthClientTokenMapper mapper;

    public OauthClientToken createOrUpdate(DiscordResponse token) {
        
    }
}
