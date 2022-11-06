package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.upe.observatorio.projeto.domain.Curso;
import com.upe.observatorio.projeto.domain.dto.CursoDTO;

public interface CursoService {
	
	public List<Curso> listarCursos();
	
	public Optional<Curso> buscarCursoPorId(Long id);
	
	public Curso adicionarCurso(CursoDTO curso);
	
	public void atualizarCurso(CursoDTO curso, Long id);
	
	public void removerCurso(Long id);
	
	public Map<String, Object> filtrarCursoPorNome(String nome, int pag, int tamanho);
	
}
