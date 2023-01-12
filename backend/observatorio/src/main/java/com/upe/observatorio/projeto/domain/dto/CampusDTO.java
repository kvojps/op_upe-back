package com.upe.observatorio.projeto.domain.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CampusDTO {
	
	@Schema(example = "UPE Multicampi - Garanhuns", description = "Nome do campus da universidade" )
	@NotBlank
	private String nome;

	@Schema(example = "Garanhuns", description = "Cidade onde o campus está localizado")
	@NotBlank
	private String cidade;
	
	@Schema(example = "São José", description = "Bairro onde o campus está localizado")
	@NotBlank
	private String bairro;
	
	@Schema(example = "Capitão Pedro Rodrigues", description = "Rua onde o campus está localizado")
	@NotBlank
	private String rua;
}
