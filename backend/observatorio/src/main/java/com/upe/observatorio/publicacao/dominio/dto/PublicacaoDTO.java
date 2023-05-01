package com.upe.observatorio.publicacao.dominio.dto;

import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.usuario.dominio.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PublicacaoDTO {
	@Schema(example="1", description="id do projeto")
	private Projeto projeto;
	
	@Schema(example="1", description="id do usuario")
	private Usuario usuario;
}
