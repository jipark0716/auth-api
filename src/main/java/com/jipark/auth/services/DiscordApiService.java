package com.jipark.auth.services;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.SelfUser;
import org.springframework.stereotype.Component;

@Component
public class DiscordApiService {
    public JDA createJDA(String token) {
        return JDABuilder.createDefault(token).build();
    }

    public SelfUser getUser()
    {
        return createJDA("OTU5MjMyNDI1MDc3MTE2OTY5.G607AJ.e66shFag1HBDXoAjoh6aUnHHnrPIh0OVro2-6E")
                .getSelfUser();
    }
}