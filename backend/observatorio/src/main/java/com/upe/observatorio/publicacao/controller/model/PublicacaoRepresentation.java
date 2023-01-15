package com.upe.observatorio.publicacao.controller.model;

import java.io.Serializable;

import com.upe.observatorio.projeto.controller.model.ProjetoRepresentation;

import lombok.Data;

@Data
public class PublicacaoRepresentation implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Integer curtidas;
	
	private Integer descurtidas;
	
	private ProjetoRepresentation projeto;
}
