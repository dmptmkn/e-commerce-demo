package com.example.infrastructure.web.rest.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    int statusCode;
    String message;

    public static ErrorResponse of(HttpStatus httpStatus, String message) {
        return new ErrorResponse(httpStatus.value(), message);
    }
}
