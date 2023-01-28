package com.upe.observatorio.publicacao.controller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ComentarioRepresentation implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@Schema(example="100", description="Quantidade de curtidas de uma publicação")
	private Integer curtidas;
	
	@Schema(example="1", description="Quantidade de descurtidas de uma publicação")
	private Integer descurtidas;
	
	@Schema(example="Projeto bastante interessante", description="Texto referente ao comentário de uma publicação")
	private String mensagem;
	
	@JsonIgnore
	private PublicacaoRepresentation publicacao;
}
