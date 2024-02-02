package com.jipark.auth.mappers.game;

import com.jipark.auth.entities.game.OauthClientToken;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OauthClientTokenMapper {
    List<OauthClientToken> get();
}
