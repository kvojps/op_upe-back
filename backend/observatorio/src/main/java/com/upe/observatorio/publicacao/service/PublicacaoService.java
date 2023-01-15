package com.upe.observatorio.publicacao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.domain.Projeto;
import com.upe.observatorio.projeto.service.ProjetoService;
import com.upe.observatorio.projeto.utilities.ObservatorioException;
import com.upe.observatorio.publicacao.domain.Publicacao;
import com.upe.observatorio.publicacao.domain.dto.PublicacaoDTO;
import com.upe.observatorio.publicacao.repository.PublicacaoRepository;

@Service
public class PublicacaoService {
	
	@Autowired
	private PublicacaoRepository repositorio;
	
	@Autowired
	private ProjetoService projetoService;
	
	public List<Publicacao> listarPublicacoes() {
		return repositorio.findAll();
	}
	
	public Optional<Publicacao> buscarPublicacaoPorId(Long id) {
		return repositorio.findById(id);
	}
	
	public Publicacao adicionarPublicacao(PublicacaoDTO publicacao) throws ObservatorioException {
		if (projetoService.buscarProjetoPorId(publicacao.getProjetoId()).isEmpty()) {
			throw new ObservatorioException("Não existe um projeto associado a este id");
		}
		
		Projeto projetoSalvar = projetoService.buscarProjetoPorId(publicacao.getProjetoId()).get();
		Publicacao publicacaoSalvar = new Publicacao();
		publicacaoSalvar.setCurtidas(0);
		publicacaoSalvar.setDescurtidas(0);
		publicacaoSalvar.setProjeto(projetoSalvar);
		
		return repositorio.save(publicacaoSalvar);
	}
	
	public void removerPublicacao(Long id) throws ObservatorioException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioException("Não existe um projeto associado a este id");
		}
		
		repositorio.deleteById(id);
	}
}
