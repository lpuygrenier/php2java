package com.example.demo.web;

import com.example.demo.service.exception.BadRequestAlertException;
import com.example.demo.service.exception.InternalServerErrorAlertException;
import com.example.demo.service.exception.NotFoundAlertException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe permet de centraliser la gestion des erreures (exceptions) dans l'application.
 * Si une exception est levée dans n'importe quel contrôleur, elle sera interceptée ici,
 * puis une réponse HTTP appropriée sera renvoyée au frontend.
 */
@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(BadRequestAlertException.class)
    @ResponseBody
    public ResponseEntity<Object> handleBadRequest(BadRequestAlertException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getEntity(), ex.getCode(), ex.getErrMsg());
    }

    @ExceptionHandler(NotFoundAlertException.class)
    @ResponseBody
    public ResponseEntity<Object> handleNotFound(NotFoundAlertException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getEntity(), ex.getCode(), ex.getErrMsg());
    }

    @ExceptionHandler(InternalServerErrorAlertException.class)
    @ResponseBody
    public ResponseEntity<Object> handleInternalError(InternalServerErrorAlertException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getEntity(), ex.getCode(), ex.getErrMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown", "internal.error", "An unexpected error occurred.");
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String entity, String code, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("entity", entity);
        body.put("code", code);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
