package com.example.spring_reactive_mongo_jwt.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class JWTUtil {

    @Value("${jjwt.secret}")
    private String secret;

    @Value("${token.expiration.in.hour}")
    private int expirationTimeInHour;

    private Key key;

    public static final String CLAIM_ROLE = "role";

    public Mono<Claims> getAllClaimsFromToken(String token){
        return Mono.just(
                Jwts.parser().setSigningKey(getKey()).build()
                        .parseClaimsJws(token).getBody()
        );
    }
    public String generateToken(UserDetails userDetails) {
        String authority = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining());
        Map<String, String> claims = new HashMap<>();
        claims.put(CLAIM_ROLE, authority);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(expirationTimeInHour))))
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getKey())
                .compact();
    }

    public Mono<Boolean> validateToken(String token) {
        return getAllClaimsFromToken(token)
                .map(Claims::getExpiration)
                .map(expiration -> expiration.after(new Date()));
    }

    private Key getKey(){
        if (key == null) {
            key = Keys.hmacShaKeyFor(secret.getBytes());
        }
        return key;
    }
}
