package com.upe.observatorio.usuario.controller.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class PerfilRepresentation implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nome;
}
