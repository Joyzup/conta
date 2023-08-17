package com.factory.contabancaria.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler (NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException exception){
        String msg = "Este elemento não existe. " + exception.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable (HttpMessageNotReadableException exception){
        String msgException = "O corpo da requisição não está legível. " + exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msgException);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException (NullPointerException exception){
        String msgException = "Erro interno do servidor. ";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msgException);
    }

}
