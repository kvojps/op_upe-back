package com.upe.observatorio.usuario.dominio.dto;

import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UsuarioDTO {
	
	@Schema(example = "José", description = "Nome do usuário")
	@NotBlank
	private String nome;
	
	@Schema(example = "jose@gmail.com", description = "E-mail de um usuário")
	@NotBlank
	private String email;
	
	@Schema(example = "L@Sws542s!", description = "Senha de um usuário")
	@NotBlank
	private String senha;
	
	@Schema(example = "20221412", description = "Matrícula de um usuário")
	private String matricula;
}
