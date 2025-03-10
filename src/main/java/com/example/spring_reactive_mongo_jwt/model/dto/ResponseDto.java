package com.example.spring_reactive_mongo_jwt.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto {
    Object data;
}
