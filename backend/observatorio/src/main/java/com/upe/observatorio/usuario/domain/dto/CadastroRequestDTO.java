package com.upe.observatorio.usuario.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CadastroRequestDTO {

	private String nome;
	
	private String email;
	
	private String senha;
}
