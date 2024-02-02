package com.jipark.auth.repositories;

import com.jipark.auth.repositories.game.OauthClientTokenRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OauthClientTokenRepositoryTest {
    public final OauthClientTokenRepository repository;

    @Test
    public void get() {
        var result = repository.get();
        Assertions.assertTrue(true);
    }
}
