package com.upe.observatorio.publicacao.dominio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PublicacaoDTO {
	@Schema(example="1", description="id do projeto")
	private Long projetoId;
	
	@Schema(example="1", description="id do usuario")
	private Long usuarioId;
}
