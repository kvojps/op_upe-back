package com.upe.observatorio.publicacao.controller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upe.observatorio.projeto.controller.model.ProjetoRepresentation;
import com.upe.observatorio.usuario.controller.model.UsuarioRepresentation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PublicacaoRepresentation implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@Schema(example="100", description="Quantidade de curtidas de uma publicação")
	private Integer curtidas;
	
	@Schema(example="1", description="Quantidade de descurtidas de uma publicação")
	private Integer descurtidas;
	
	@Schema(example="1000", description="Quantidade de visualizações de uma publicação")
	private Integer visualizacoes;
	
	@JsonIgnore
	private ProjetoRepresentation projeto;
	
	@JsonIgnore
	private UsuarioRepresentation usuario;
}
