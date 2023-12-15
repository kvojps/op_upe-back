package com.upe.observatorio.projeto.model.vos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StatusExecucaoVO {
    private String mensagem;
    private String causa;
}
