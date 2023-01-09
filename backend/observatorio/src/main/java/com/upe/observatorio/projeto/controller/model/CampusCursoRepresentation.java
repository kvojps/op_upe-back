package com.upe.observatorio.projeto.controller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CampusCursoRepresentation  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@JsonIgnore
	private CampusRepresentation campus;
	
	@JsonIgnore
	private CursoRepresentation curso;
}
