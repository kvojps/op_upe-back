package com.upe.observatorio.projeto.controller.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CursoRepresentation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nome;
	
	private List<CampusCursoRepresentation> campusCurso;
	
	private List<CursoProjetoRepresentation> cursoProjeto;
}
