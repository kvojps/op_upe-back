package com.upe.observatorio.projeto.domain.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CursoDTO {
	
	@NotBlank
	private String nome;
}
