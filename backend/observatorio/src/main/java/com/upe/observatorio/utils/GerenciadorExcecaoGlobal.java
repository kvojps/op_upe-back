package com.upe.observatorio.utils;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GerenciadorExcecaoGlobal {
    @ExceptionHandler(ObservatorioExcecao.class)
    public ResponseEntity<Object> handleAcsException(ObservatorioExcecao ex) {
        return ResponseEntity.badRequest().body(new GlobalExceptionHandlerResponse(ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GlobalExceptionHandlerResponse(ex.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Object> handleInvalidPasswordException(InvalidPasswordException ex) {
        return ResponseEntity.badRequest().body(new GlobalExceptionHandlerResponse(ex.getMessage()));
    }

    @ExceptionHandler(RelationExistsException.class)
    public ResponseEntity<Object> handleCampusCursoRelationException(RelationExistsException ex) {
        return ResponseEntity.badRequest().body(new GlobalExceptionHandlerResponse(ex.getMessage()));
    }

    @ExceptionHandler(ProjectResourceNotFoundException.class)
    public ResponseEntity<Object> handleProjectResourceNotFoundException(ProjectResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GlobalExceptionHandlerResponse(ex.getMessage()));
    }
}