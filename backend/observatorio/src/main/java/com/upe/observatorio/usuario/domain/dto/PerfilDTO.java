package com.upe.observatorio.usuario.domain.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PerfilDTO {
	
	@NotBlank
	private String nome;
}
