package com.upe.observatorio.usuario.controller.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UsuarioRepresentation implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nome; 
	
	private String email;
	
	private String senha;
	
	private String matricula;
	
	private List<PerfilRepresentation> perfis;
}
