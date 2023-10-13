package com.upe.observatorio.utils;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GerenciadorExcecaoGlobal {
    @ExceptionHandler(ObservatorioExcecao.class)
    public ResponseEntity<Object> handleAcsException(ObservatorioExcecao ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
