package com.upe.observatorio.projeto.controller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CursoProjetoRepresentation implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Schema(example = "1", description = "Id referente ao cursoProjeto")
	private Long id;
	
	@JsonIgnore
	private CursoRepresentation curso;
	
	@JsonIgnore
	private ProjetoRepresentation projeto;
}
