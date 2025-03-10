package com.example.spring_reactive_mongo_jwt.repository;

import com.example.spring_reactive_mongo_jwt.model.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository <User, String>{

    Mono<UserDetails> findByEmail(final String email);
    Mono<User> findUserByEmail(final String email);

}
