package com.jipark.auth;

import com.azure.security.keyvault.secrets.SecretClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
}