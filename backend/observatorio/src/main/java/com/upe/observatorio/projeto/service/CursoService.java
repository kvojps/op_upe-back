package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.upe.observatorio.projeto.domain.Curso;
import com.upe.observatorio.projeto.domain.dto.CursoDTO;
import com.upe.observatorio.projeto.utilities.ProjetoException;

public interface CursoService {
	
	public List<Curso> listarCursos();
	
	public Optional<Curso> buscarCursoPorId(Long id);
	
	public Curso adicionarCurso(CursoDTO curso) throws ProjetoException;
	
	public void atualizarCurso(CursoDTO curso, Long id) throws ProjetoException;
	
	public void removerCurso(Long id) throws ProjetoException;
	
	public Map<String, Object> filtrarCursoPorNome(String nome, int pag, int tamanho);
	
}
