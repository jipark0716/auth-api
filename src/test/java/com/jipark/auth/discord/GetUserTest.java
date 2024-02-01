package com.jipark.auth.discord;

import com.jipark.auth.services.DiscordApiService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GetUserTest {
    public final DiscordApiService discordApiService;

    @Test
    public void test()
    {
        discordApiService.getUser();
        assertTrue(true);
    }
}
