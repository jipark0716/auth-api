package com.jipark.auth.repositories;

import com.jipark.auth.dtos.oauth.JwtPayload;
import com.jipark.auth.entities.emuns.OauthProviderType;
import com.jipark.auth.entities.game.User;
import com.jipark.auth.repositories.game.OauthClientTokenRepository;
import com.jipark.auth.repositories.game.UserRepository;
import com.jipark.auth.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OauthClientTokenRepositoryTest {
    private final OauthClientTokenRepository repository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Test
    public void get() {
//        var result = repository.findAll().blockFirst();
//        var a = userRepository.findById(11L);
        var a = userRepository.save(new User("name"));
        var v = a.block();

        Assertions.assertTrue(true);
    }

    @Test
    public void refresh() {
//        var a = "eyJyZWdEYXRlIjoxNzA3MTA2NzUwOTMxLCJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidG9rZW5fdHlwZSI6ImFjY2VzcyIsInN1YiI6InVzZXIiLCJleHAiOjE3MDcxMTAzNTB9.xA0XjrFD5tt5FfEKqiLGfpvIOcET37zOUQADiRVAmh6EFIl9nOGmH5yPed2xcd8tjy_PxlZzRUaErWTiL2WyHQ";
        var a = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidG9rZW5fdHlwZSI6InJlZnJlc2giLCJzdWIiOiJ1c2VyIiwiZXhwIjoxNzA3NzE0NTQxfQ.fghX2gIPsK0JSc8cWsMeQ4JSbMzw9KS9JFgMKy8vYsE";
        var token = jwtService.read(a, JwtPayload.class);

        Assertions.assertTrue(true);
    }
}
