package com.upe.observatorio.sheet.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StatusExecutionResponse {
    private String mensagem;
    private String causa;
}
