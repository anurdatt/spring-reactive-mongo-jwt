package com.example.spring_reactive_mongo_jwt.service;

import com.example.spring_reactive_mongo_jwt.model.dto.UserDto;
import reactor.core.publisher.Mono;

public interface UserService {
    public Mono<UserDto> getUser(String email);
}
