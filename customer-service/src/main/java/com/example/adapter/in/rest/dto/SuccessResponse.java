package com.example.adapter.in.rest.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SuccessResponse {

    int statusCode;
    String message;

    public static SuccessResponse of(HttpStatus httpStatus, String message) {
        return new SuccessResponse(httpStatus.value(), message);
    }
}
