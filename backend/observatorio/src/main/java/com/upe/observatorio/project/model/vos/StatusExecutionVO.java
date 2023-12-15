package com.upe.observatorio.project.model.vos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StatusExecutionVO {
    private String mensagem;
    private String causa;
}
