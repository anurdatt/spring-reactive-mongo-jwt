package com.example.spring_reactive_mongo_jwt.model.mapper;

import com.example.spring_reactive_mongo_jwt.model.dto.UserDto;
import com.example.spring_reactive_mongo_jwt.model.entity.User;
import com.example.spring_reactive_mongo_jwt.model.enums.UserRoles;

public class UserEntityDtoMapper {

    public static User fromDTO(UserDto userDto) {
        return User.builder().firstName(userDto.getName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .active(true)
                .role(UserRoles.MEMBER)
                .build();
    }

    public static UserDto toDTO(User user) {
        return UserDto.builder()
                .name(user.getFirstName())
                .email(user.getEmail())
                .build();
    }
}
