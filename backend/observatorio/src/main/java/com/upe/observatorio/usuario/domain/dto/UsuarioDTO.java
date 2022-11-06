package com.upe.observatorio.usuario.domain.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsuarioDTO {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String senha;
	
	private String matricula;
}
