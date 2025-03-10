package com.example.spring_reactive_mongo_jwt.service.impl;

import com.example.spring_reactive_mongo_jwt.model.dto.UserDto;
import com.example.spring_reactive_mongo_jwt.model.mapper.UserEntityDtoMapper;
import com.example.spring_reactive_mongo_jwt.repository.UserRepository;
import com.example.spring_reactive_mongo_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Mono<UserDto> getUser(String email) {
        return userRepository.findUserByEmail(email)
                .map(UserEntityDtoMapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found!")));
    }
}
