package com.example.spring_reactive_mongo_jwt.service.impl;

import com.example.spring_reactive_mongo_jwt.model.dto.ResponseDto;
import com.example.spring_reactive_mongo_jwt.model.dto.UserDto;
import com.example.spring_reactive_mongo_jwt.model.mapper.UserEntityDtoMapper;
import com.example.spring_reactive_mongo_jwt.repository.UserRepository;
import com.example.spring_reactive_mongo_jwt.security.JWTUtil;
import com.example.spring_reactive_mongo_jwt.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Mono<UserDto> signup(UserDto userDto) {
        return isUserExist(userDto.getEmail())
                .filter(abool -> !abool)
                .switchIfEmpty(Mono.error(new RuntimeException("User Already Exists!")))
                .map(abool -> userDto)
                .doOnNext(uDto -> uDto.setPassword(passwordEncoder.encode(uDto.getPassword())))
//                .map(UserMapper.INSTANCE::fromDTO)
                .map(UserEntityDtoMapper::fromDTO)
                .flatMap(repository::save)
                .map(UserEntityDtoMapper::toDTO);
//                .map(UserMapper.INSTANCE::toDTO);

    }


    @Override
    public Mono<ResponseDto> login(String email, String password) {
        return repository.findByEmail(email)
                .filter(userDetails -> passwordEncoder.matches(password, userDetails.getPassword()))
                .map(userDetails -> jwtUtil.generateToken(userDetails))
                .map(token -> ResponseDto.builder().data(token).build())
                .switchIfEmpty(Mono.error(new RuntimeException("Wrong credentials!")));
    }

    private Mono<Boolean> isUserExist(String email) {
        return repository.findByEmail(email)
                .map(user -> true)
                .switchIfEmpty(Mono.just(false));
    }
}
