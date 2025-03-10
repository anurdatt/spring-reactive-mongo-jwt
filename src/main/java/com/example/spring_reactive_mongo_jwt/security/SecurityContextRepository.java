package com.example.spring_reactive_mongo_jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    AuthenticationManager authenticationManager;

    private static final String AUTH_TOKEN_PREFIX = "Bearer ";

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest())
                .map(request -> request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(token -> token != null && token.startsWith(AUTH_TOKEN_PREFIX))
                .map(token -> token.substring(AUTH_TOKEN_PREFIX.length()))
                .map(authToken -> new UsernamePasswordAuthenticationToken(authToken, authToken))
                .flatMap(authenticationManager::authenticate)
                .map(SecurityContextImpl::new);
    }
}
