package com.upe.observatorio.publicacao.controlador.modelo;

import com.upe.observatorio.publicacao.dominio.Comentario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ComentarioRepresentacao {

	private Long id;
	
	@Schema(example="100", description="Quantidade de curtidas de uma publicação")
	private Integer curtidas;
	
	@Schema(example="1", description="Quantidade de descurtidas de uma publicação")
	private Integer descurtidas;
	
	@Schema(example="Projeto bastante interessante", description="Texto referente ao comentário de uma publicação")
	private String mensagem;

	public ComentarioRepresentacao(Comentario comentario) {
		this.id = comentario.getId();
		this.curtidas = comentario.getCurtidas();
		this.descurtidas = comentario.getDescurtidas();
		this.mensagem = comentario.getMensagem();
	}
}
