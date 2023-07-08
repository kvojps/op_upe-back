package com.upe.observatorio.usuario.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CadastroRequestDTO {

	private String nome;

	@Email(message = "Email inválido")
	private String email;
	
	private String senha;
}
