package com.jipark.auth.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jipark.auth.http.response.JwtResponse;
import com.jipark.auth.entities.game.User;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
    private final JwtParser parser;
    private final SecretKey key;
    @Value("${jwt.expires-in}")
    private int expiresIn;

    @Value("${jwt.refresh-expires-in}")
    private int refreshExpiresIn;

    private JwtService(@Value("${jwt.key}") String encodedKey) {
        key = new SecretKeySpec(encodedKey.getBytes(), "HmacSHA256");
        parser = Jwts.parser().verifyWith(key).build();
    }

    public <T> T read(String token, Class<T> type) {
        Claims payload;
        try {
            payload = parser.parseSignedClaims(token).getPayload();
        } catch (JwtException e) {
            return null;
        }

        T result;
        try {
            result = type.getDeclaredConstructor().newInstance();
        } catch (Throwable e) {
            return null;
        }
        for (var field : type.getDeclaredFields()) {
            var nameAttribute = field.getAnnotation(JsonProperty.class);
            var fieldName = nameAttribute == null ? field.getName() : nameAttribute.value();
            field.setAccessible(true);
            try {
                field.set(result, payload.get(fieldName));
            } catch(IllegalAccessException e) {
                return null;
            }
        }
        return result;
    }

    public JwtResponse generateJwtToken(User user) {
        return new JwtResponse(
                getAccessToken(user),
                getRefreshToken(user),
                expiresIn
        );
    }

    public String getAccessToken(User user) {
        return getToken(new HashMap<>() {{
            put("id", user.getId());
            put("token_type", "access");
        }}, expiresIn);
    }

    public String getRefreshToken(User user) {
        return getToken(new HashMap<>() {{
            put("id", user.getId());
            put("token_type", "refresh");
        }}, refreshExpiresIn);
    }

    private String getToken(Map<String, Object> claims, int expiresIn) {
        var result = Jwts.builder()
                .claims(claims)
                .subject("user")
                .signWith(key)
                .expiration(new Date(System.currentTimeMillis() + (1000L * expiresIn)));
        return result.compact();
    }
}
