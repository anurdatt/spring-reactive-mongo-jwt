package com.example.spring_reactive_mongo_jwt.controller.v1;

import com.example.spring_reactive_mongo_jwt.model.dto.ResponseDto;
import com.example.spring_reactive_mongo_jwt.model.dto.UserDto;
import com.example.spring_reactive_mongo_jwt.service.AuthService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth/v1")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/signup")
    public Mono<UserDto> signup(@RequestBody UserDto userDto) {
        System.out.println("Debug..");
        log.info("User email = {}", userDto.getEmail());
        return service.signup(userDto);
    }

    @GetMapping("/login")
    public Mono<ResponseDto> login(@RequestHeader("email") String email, @RequestHeader("password") String password) {
        return service.login(email, password);
    }
}
