package com.jipark.auth.repositories;

import com.jipark.auth.entities.emuns.OauthProviderType;
import com.jipark.auth.entities.game.User;
import com.jipark.auth.repositories.game.OauthClientTokenRepository;
import com.jipark.auth.repositories.game.UserRepository;
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
    public final OauthClientTokenRepository repository;
    public final UserRepository userRepository;

    @Test
    public void get() {
//        var result = repository.findAll().blockFirst();
//        var a = userRepository.findById(11L);
        var a = userRepository.save(new User("name"));
        var v = a.block();

        Assertions.assertTrue(true);
    }
}
