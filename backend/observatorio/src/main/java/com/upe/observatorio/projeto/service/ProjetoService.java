package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.upe.observatorio.projeto.domain.Projeto;
import com.upe.observatorio.projeto.domain.dto.ProjetoDTO;

public interface ProjetoService {
	
	public List<Projeto> listarProjetos();
	
	public Optional<Projeto> buscarProjetoPorId(Long id);
	
	public Projeto adicionarProjeto(ProjetoDTO projeto);
	
	public void atualizarProjeto(ProjetoDTO projeto, Long id);
	
	public void removerProjeto(Long id);
	
	public Map<String, Object> filtrarProjetoPorAreaTematica(String areaTematica, int pag, int tamanho);
	
	public Map<String, Object> filtrarProjetoPorModalidade(String modalidade, int pag, int tamanho);
	
	public Map<String, Object> filtrarProjetoPorTitulo(String titulo, int page, int tamanho);
	
}
