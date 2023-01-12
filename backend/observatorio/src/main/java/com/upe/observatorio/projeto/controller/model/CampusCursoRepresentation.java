package com.upe.observatorio.projeto.controller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CampusCursoRepresentation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(example = "1", description = "Id referente ao campusCurso")
	private Long id;

	@JsonIgnore
	private CampusRepresentation campus;

	@JsonIgnore
	private CursoRepresentation curso;
}
