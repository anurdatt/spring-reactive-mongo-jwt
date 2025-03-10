package com.example.spring_reactive_mongo_jwt.controller.v1;

import com.example.spring_reactive_mongo_jwt.model.dto.UserDto;
import com.example.spring_reactive_mongo_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/user/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Mono<UserDto> getUser(Principal principal) {
        return userService.getUser(principal.getName());
    }
}
