package com.upe.observatorio.publicacao.dominio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AtualizarComentarioDTO {
	
	@Schema(example="Projeto bastante interessante", description="Texto referente ao comentário de uma publicação")
	private String mensagem;
}
