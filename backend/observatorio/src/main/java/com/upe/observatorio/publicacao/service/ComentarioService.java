package com.upe.observatorio.publicacao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.publicacao.domain.Comentario;
import com.upe.observatorio.publicacao.domain.Publicacao;
import com.upe.observatorio.publicacao.domain.dto.AtualizarComentarioDTO;
import com.upe.observatorio.publicacao.domain.dto.ComentarioDTO;
import com.upe.observatorio.publicacao.repository.ComentarioRepository;
import com.upe.observatorio.utils.ObservatorioException;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository repositorio;
	
	@Autowired
	private PublicacaoService publicacaoServico;
	
	public List<Comentario> listarComentarios() {
		return repositorio.findAll();
	}
	
	public Optional<Comentario> buscarComentarioPorId(Long id) {
		return repositorio.findById(id);
	}
	
	public Comentario adicionarComentario(ComentarioDTO comentario) throws ObservatorioException{
		if (publicacaoServico.buscarPublicacaoPorId(comentario.getPublicacaoId()).isEmpty()) {
			throw new ObservatorioException("Não existe uma publicação associada a este id");
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
	
	public void atualizarComentario(AtualizarComentarioDTO comentario, Long id) throws ObservatorioException{
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioException("Não existe um comentario associado a este id");
		}
		
		Comentario comentarioExistente = repositorio.findById(id).get();
		if (!comentarioExistente.getMensagem().equals(comentario.getMensagem())) {
			comentarioExistente.setMensagem(comentario.getMensagem());
		}
		
		repositorio.save(comentarioExistente);
	}
	
	public void removerComentario(Long id) throws ObservatorioException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioException("Não existe um comentario associado a este id");
		}
		
		repositorio.deleteById(id);
	}
}
