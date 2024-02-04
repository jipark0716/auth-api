package com.jipark.auth.services;

import com.jipark.auth.dtos.response.JwtResponse;
import com.jipark.auth.entities.game.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
    private final Key key;
    @Value("${jwt.expires-in}")
    private int expiresIn;

    @Value("${jwt.refresh-expires-in}")
    private int refreshExpiresIn;

    private JwtService(@Value("${jwt.key}") String encodedKey) {
        key = Keys.hmacShaKeyFor(DatatypeConverter.parseBase64Binary(encodedKey));
    }

    public JwtResponse generateJwtToken(User user) {
        return new JwtResponse(
                getAccessToken(user),
                getRefreshToken(user),
                expiresIn
        );
    }

    public String getAccessToken(User user) {
        var result = Jwts.builder()
                .claims(new HashMap<>() {{
                    put("id", user.getId());
                    put("token_type", "access");
                }})
                .subject("user")
                .signWith(key)
                .expiration(new Date(System.currentTimeMillis() + (1000L * expiresIn)));
        result.header().add(createHeader());
        return result.compact();
    }

    public String getRefreshToken(User user) {
        var result = Jwts.builder()
                .claims(new HashMap<>() {{
                    put("id", user.getId());
                    put("token_type", "refresh");
                }})
                .subject("user")
                .signWith(key)
                .expiration(new Date(System.currentTimeMillis() + (1000L * refreshExpiresIn)));
        result.header().add(createHeader());
        return result.compact();
    }

    private Map<String, Object> createHeader() {
        return new HashMap<>() {{
            put("typ", "JWT");
            put("alg", "HS256");
            put("regDate", System.currentTimeMillis());
        }};
    }
}
