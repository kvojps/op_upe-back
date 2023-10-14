package com.upe.observatorio.publicacao.controlador.modelo;

import com.upe.observatorio.projeto.controlador.representacao.ProjetoRepresentacao;
import com.upe.observatorio.publicacao.dominio.Comentario;
import com.upe.observatorio.publicacao.dominio.Publicacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PublicacaoRepresentacao {

	private Long id;
	
	@Schema(example="100", description="Quantidade de curtidas de uma publicação")
	private Integer curtidas;
	
	@Schema(example="1", description="Quantidade de descurtidas de uma publicação")
	private Integer descurtidas;
	
	@Schema(example="1000", description="Quantidade de visualizações de uma publicação")
	private Integer visualizacoes;

	private ProjetoRepresentacao projeto;
	
	public List<ComentarioRepresentacao> comentarios;

	public PublicacaoRepresentacao(Publicacao publicacao) {
		this.id = publicacao.getId();
		this.curtidas = publicacao.getCurtidas();
		this.descurtidas = publicacao.getDescurtidas();
		this.visualizacoes = publicacao.getDescurtidas();
		this.projeto = new ProjetoRepresentacao(publicacao.getProjeto());
		this.comentarios = converterComentarios(publicacao.getComentarios());
	}

	private List<ComentarioRepresentacao> converterComentarios(List<Comentario> comentarios) {
		return comentarios.stream().map(ComentarioRepresentacao::new).collect(Collectors.toList());
	}
}
