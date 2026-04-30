package com.example.infrastructure.web.rest;

import lombok.Value;

@Value(staticConstructor = "of")
public class SuccessResponse {
    String message;
    String statusCode;
}