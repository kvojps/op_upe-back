package com.upe.observatorio.projeto.domain.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CursoProjetoDTO {
	
	@NotNull
	private Long cursoId;
	
	@NotNull
	private Long projetoId;
	
}
