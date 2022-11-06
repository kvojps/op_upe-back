package com.upe.observatorio.projeto.domain.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CampusCursoDTO {
	
	@NotNull
	private Long campusId;
	
	@NotNull
	private Long cursoId;
}
