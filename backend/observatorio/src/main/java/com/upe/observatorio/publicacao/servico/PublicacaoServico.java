package com.upe.observatorio.publicacao.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.publicacao.dominio.Publicacao;
import com.upe.observatorio.publicacao.dominio.dto.PublicacaoDTO;
import com.upe.observatorio.publicacao.repositorio.PublicacaoRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;

@Service
public class PublicacaoServico {
	
	@Autowired
	private PublicacaoRepositorio repositorio;
	
	public List<Publicacao> listarPublicacoes() {
		return repositorio.findAll();
	}
	
	public Optional<Publicacao> buscarPublicacaoPorId(Long id) throws ObservatorioExcecao {
		Optional<Publicacao> publicacao = repositorio.findById(id);
		
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe uma publicação associada a este id!");
		}
		
		publicacao.get().setVisualizacoes(publicacao.get().getVisualizacoes() + 1);
		repositorio.save(publicacao.get());
		
		return repositorio.findById(id);
	}
	
	public Publicacao adicionarPublicacao(PublicacaoDTO publicacao) throws ObservatorioExcecao {
		
		Publicacao publicacaoSalvar = new Publicacao();
		publicacaoSalvar.setCurtidas(0);
		publicacaoSalvar.setDescurtidas(0);
		publicacaoSalvar.setVisualizacoes(0);
		publicacaoSalvar.setProjeto(publicacao.getProjeto());
		publicacaoSalvar.setUsuario(publicacao.getUsuario());
		
		return repositorio.save(publicacaoSalvar);
	}
	
	public void removerPublicacao(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um projeto associado a este id");
		}
		
		repositorio.deleteById(id);
	}
	
	public void adicionarCurtida(Long id) throws ObservatorioExcecao {
		Publicacao publicacaoToSave = buscarPublicacaoPorId(id).get();
		publicacaoToSave.setCurtidas(publicacaoToSave.getCurtidas() + 1);
		
		repositorio.save(publicacaoToSave);
	}
	
	public void adicionarDescurtida(Long id) throws ObservatorioExcecao {
		Publicacao publicacaoToSave = buscarPublicacaoPorId(id).get();
		publicacaoToSave.setDescurtidas(publicacaoToSave.getDescurtidas() + 1);
		
		repositorio.save(publicacaoToSave);
	}
	
	public void removerCurtida(Long id) throws ObservatorioExcecao {
		Publicacao publicacaoToSave = buscarPublicacaoPorId(id).get();
		publicacaoToSave.setCurtidas(publicacaoToSave.getCurtidas() - 1);
		
		repositorio.save(publicacaoToSave);
	}
	
	public void removerDescurtida(Long id) throws ObservatorioExcecao {
		Publicacao publicacaoToSave = buscarPublicacaoPorId(id).get();
		publicacaoToSave.setDescurtidas(publicacaoToSave.getDescurtidas() - 1);
		
		repositorio.save(publicacaoToSave);
	}
}
