package com.upe.observatorio.publicacao.domain.dto;

import lombok.Data;

@Data
public class PublicacaoDTO {
	private Integer curtidas;
	
	private Integer descurtidas;
	
	private Long projetoId;
	
	private Long usuarioId;
}
