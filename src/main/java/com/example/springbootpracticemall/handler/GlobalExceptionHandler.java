package com.example.springbootpracticemall.handler;

import com.example.springbootpracticemall.model.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusCode(), ex.getReason());
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

}


