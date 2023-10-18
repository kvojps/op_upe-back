package com.upe.observatorio.projeto.dominio.dto;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CursoProjetoDTO {
	
	@Schema(example = "1", description = "Id do curso")
	@NotNull
	private Long cursoId;
	
	@Schema(example = "2", description = "Id do projeto")
	@NotNull
	private Long projetoId;
	
}
