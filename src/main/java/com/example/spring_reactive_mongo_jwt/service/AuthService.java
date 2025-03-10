package com.example.spring_reactive_mongo_jwt.service;

import com.example.spring_reactive_mongo_jwt.model.dto.ResponseDto;
import com.example.spring_reactive_mongo_jwt.model.dto.UserDto;
import reactor.core.publisher.Mono;

public interface AuthService {
    public Mono<UserDto> signup(UserDto userDto);

    public Mono<ResponseDto> login(String email, String password);
}
