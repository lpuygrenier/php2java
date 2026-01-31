package com.example.demo.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InternalServerErrorAlertException extends RuntimeException {
    private final String entity;
    private final String code;
    private final String errMsg;
}
