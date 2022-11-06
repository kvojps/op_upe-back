package com.upe.observatorio.projeto.domain.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CampusDTO {
	
	@NotBlank
	private String nome;

	@NotBlank
	private String cidade;
	
	@NotBlank
	private String bairro;
	
	@NotBlank
	private String rua;
}
