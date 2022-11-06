package com.upe.observatorio.projeto.controller.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class CampusCursoRepresentation  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private CampusRepresentation campus;
	
	private CursoRepresentation curso;
}
