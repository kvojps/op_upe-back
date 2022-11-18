package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.upe.observatorio.projeto.domain.Campus;
import com.upe.observatorio.projeto.domain.dto.CampusDTO;
import com.upe.observatorio.projeto.utilities.ProjetoException;

public interface CampusService {
	
	public List<Campus> listarCampus();
	
	public Optional<Campus> buscarCampusPorId(Long id);
	
	public Campus adicionarCampus(CampusDTO campus) throws ProjetoException;
	
	public void atualizarCampus(CampusDTO campus, Long id) throws ProjetoException;
	
	public void removerCampus(Long id) throws ProjetoException;
	
	public Map<String, Object> filtrarCampusPorNome(String nome, int pag, int tamanho);
	
	public Map<String, Object> filtrarCampusPorCidade(String cidade, int pag, int tamanho);
}
