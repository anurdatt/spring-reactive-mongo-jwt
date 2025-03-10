package com.example.spring_reactive_mongo_jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static com.example.spring_reactive_mongo_jwt.security.JWTUtil.CLAIM_ROLE;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    JWTUtil jwtUtil;


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        return Mono.just(authToken)
                .map(token -> jwtUtil.validateToken(token))
                .onErrorResume(e -> Mono.empty())
                .flatMap(isValid -> jwtUtil.getAllClaimsFromToken(authToken))
                .map(claims -> new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.singletonList(new SimpleGrantedAuthority(claims.get(CLAIM_ROLE).toString()))));
    }
}
