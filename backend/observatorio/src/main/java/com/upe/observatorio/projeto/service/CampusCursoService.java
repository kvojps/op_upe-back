package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Optional;

import com.upe.observatorio.projeto.domain.CampusCurso;
import com.upe.observatorio.projeto.domain.dto.CampusCursoDTO;
import com.upe.observatorio.projeto.utilities.ProjetoException;

public interface CampusCursoService {

	public List<CampusCurso> listarCampusCurso();
	
	public Optional<CampusCurso> buscarCampusCursoPorId(Long id);
	
	public CampusCurso adicionarCampusCurso(CampusCursoDTO campusCurso) throws ProjetoException;
	
	public void atualizarCampusCurso(CampusCursoDTO campusCurso, Long id) throws ProjetoException;
	
	public void removerCampusCurso(Long id) throws ProjetoException;
}
