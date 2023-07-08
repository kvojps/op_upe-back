package com.upe.observatorio.publicacao.servico;

import com.upe.observatorio.publicacao.dominio.Comentario;
import com.upe.observatorio.publicacao.dominio.Publicacao;
import com.upe.observatorio.publicacao.dominio.dto.AtualizarComentarioDTO;
import com.upe.observatorio.publicacao.dominio.dto.ComentarioDTO;
import com.upe.observatorio.publicacao.repositorio.ComentarioRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComentarioServico {

	private final ComentarioRepositorio repositorio;
	private final PublicacaoServico publicacaoServico;
	
	public Optional<Comentario> buscarComentarioPorId(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um comentário associado a este id!");
		}

		return repositorio.findById(id);
	}
	
	public Comentario adicionarComentario(ComentarioDTO comentario) throws ObservatorioExcecao{
		if (publicacaoServico.buscarPublicacaoPorId(comentario.getPublicacaoId()).isEmpty()) {
			throw new ObservatorioExcecao("Não existe uma publicação associada a este id");
		}
		
		Publicacao publicacaoSalvar = publicacaoServico.buscarPublicacaoPorId(comentario.getPublicacaoId()).get();
		Comentario comentarioSalvar = new Comentario();
		comentarioSalvar.setCurtidas(0);
		comentarioSalvar.setDescurtidas(0);
		comentarioSalvar.setMensagem(comentario.getMensagem());
		comentarioSalvar.setPublicacao(publicacaoSalvar);
		
		repositorio.save(comentarioSalvar);
		
		return comentarioSalvar;
	}
	
	public void atualizarComentario(AtualizarComentarioDTO comentario, Long id) throws ObservatorioExcecao{
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um comentario associado a este id");
		}
		
		Comentario comentarioExistente = repositorio.findById(id).get();
		if (!comentarioExistente.getMensagem().equals(comentario.getMensagem())) {
			comentarioExistente.setMensagem(comentario.getMensagem());
		}
		
		repositorio.save(comentarioExistente);
	}
	
	public void removerComentario(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um comentario associado a este id");
		}
		
		repositorio.deleteById(id);
	}
}
