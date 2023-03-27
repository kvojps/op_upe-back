package com.upe.observatorio.publicacao.controlador.modelo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upe.observatorio.projeto.controlador.modelo.ProjetoRepresentacao;
import com.upe.observatorio.usuario.controlador.model.UsuarioRepresentacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PublicacaoRepresentacao implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@Schema(example="100", description="Quantidade de curtidas de uma publicação")
	private Integer curtidas;
	
	@Schema(example="1", description="Quantidade de descurtidas de uma publicação")
	private Integer descurtidas;
	
	@Schema(example="1000", description="Quantidade de visualizações de uma publicação")
	private Integer visualizacoes;

	@JsonIgnore
	private UsuarioRepresentacao usuario;

	private ProjetoRepresentacao projeto;
	
	public List<ComentarioRepresentacao> comentarios;
}
