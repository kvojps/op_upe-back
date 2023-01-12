package com.upe.observatorio.projeto.controller.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CampusRepresentation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(example = "1", description = "Id referente ao campus")
	private Long id;

	@Schema(example = "UPE Multicampi - Garanhuns", description = "Nome do campus da universidade" )
	private String nome;

	@Schema(example = "Garanhuns", description = "Cidade onde o campus está localizado")
	private String cidade;

	@Schema(example = "São José", description = "Bairro onde o campus está localizado")
	private String bairro;

	@Schema(example = "Capitão Pedro Rodrigues", description = "Rua onde o campus está localizado")
	private String rua;

	private List<CampusCursoRepresentation> campusCurso;
}
