package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.upe.observatorio.projeto.domain.Campus;
import com.upe.observatorio.projeto.domain.dto.CampusDTO;

public interface CampusService {
	
	public List<Campus> listarCampus();
	
	public Optional<Campus> buscarCampusPorId(Long id);
	
	public Campus adicionarCampus(CampusDTO campus);
	
	public void atualizarCampus(CampusDTO campus, Long id);
	
	public void removerCampus(Long id);
	
	public Map<String, Object> filtrarProjetoPorNome(String nome, int pag, int tamanho);
	
	public Map<String, Object> filtrarProjetoPorCidade(String cidade, int pag, int tamanho);
}
