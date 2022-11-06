package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Optional;

import com.upe.observatorio.projeto.domain.CampusCurso;
import com.upe.observatorio.projeto.domain.dto.CampusCursoDTO;

public interface CampusCursoService {

	public List<CampusCurso> listarCampusCurso();
	
	public Optional<CampusCurso> buscarCampusCursoPorId(Long id);
	
	public CampusCurso adicionarCampusCurso(CampusCursoDTO campusCurso);
	
	public void atualizarCampusCurso(CampusCursoDTO campusCurso, Long id);
	
	public void removerCampusCurso(Long id);
}
