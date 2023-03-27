package com.upe.observatorio.projeto.dominio.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CursoDTO {
	
	@Schema(example = "Engenharia de Software", description = "Nome do curso")
	@NotBlank
	private String nome;
}
