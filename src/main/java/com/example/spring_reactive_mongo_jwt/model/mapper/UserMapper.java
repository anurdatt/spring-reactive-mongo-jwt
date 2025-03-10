//package com.example.spring_reactive_mongo_jwt.model.mapper;
//
//
//import com.example.spring_reactive_mongo_jwt.model.dto.UserDto;
//import com.example.spring_reactive_mongo_jwt.model.entity.User;
//import com.example.spring_reactive_mongo_jwt.model.enums.UserRoles;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.Collections;
//import java.util.HashSet;
//
//@Mapper(imports = {HashSet.class, Collections.class, SimpleGrantedAuthority.class, UserRoles.class} )
//public interface UserMapper {
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
//
//    @Mapping(target = "password", ignore = true)
//    UserDto toDTO(final User user);
//
//    User fromDTO(final UserDto userDto);
//}
