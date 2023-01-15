package com.upe.observatorio.publicacao.controller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upe.observatorio.projeto.controller.model.ProjetoRepresentation;
import com.upe.observatorio.usuario.controller.model.UsuarioRepresentation;

import lombok.Data;

@Data
public class PublicacaoRepresentation implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Integer curtidas;
	
	private Integer descurtidas;
	
	@JsonIgnore
	private ProjetoRepresentation projeto;
	
	@JsonIgnore
	private UsuarioRepresentation usuario;
}
