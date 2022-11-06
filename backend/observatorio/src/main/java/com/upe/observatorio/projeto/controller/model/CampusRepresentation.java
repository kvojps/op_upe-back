package com.upe.observatorio.projeto.controller.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CampusRepresentation implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String nome;

	private String cidade;
	
	private String bairro;
	
	private String rua;
	
	private List<CampusCursoRepresentation> campusCurso;
}
