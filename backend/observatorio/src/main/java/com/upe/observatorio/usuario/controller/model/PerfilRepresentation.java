package com.upe.observatorio.usuario.controller.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PerfilRepresentation implements Serializable{
	private static final long serialVersionUID = 1L;

	@Schema(example = "1", description = "Id referente ao perfil")
	private Long id;
	
	@Schema(example = "COORDENADOR", description = "Nome referente ao perfil")
	private String nome;
}
