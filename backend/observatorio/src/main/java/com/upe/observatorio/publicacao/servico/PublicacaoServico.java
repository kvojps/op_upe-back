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
	
	public Optional<Publicacao> buscarPublicacaoPorId(Long id) {
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
}
