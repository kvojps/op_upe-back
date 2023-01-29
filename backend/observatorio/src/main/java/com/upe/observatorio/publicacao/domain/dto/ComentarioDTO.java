package com.upe.observatorio.publicacao.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ComentarioDTO {
	
	@Schema(example="Projeto bastante interessante", description="Texto referente ao comentário de uma publicação")
	private String mensagem;
	
	@Schema(example="14", description="Id referente a publicação")
	private Long publicacaoId;
}
