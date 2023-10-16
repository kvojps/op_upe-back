package com.upe.observatorio.projeto.dominio.envelopes;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StatusExecucaoVO {
    private String mensagem;
    private String causa;
}
