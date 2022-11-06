package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Optional;

import com.upe.observatorio.projeto.domain.CursoProjeto;
import com.upe.observatorio.projeto.domain.dto.CursoProjetoDTO;

public interface CursoProjetoService {
	
	public List<CursoProjeto> listarCursoProjetos();

	public Optional<CursoProjeto> buscarCursoProjetoPorId(Long id);

	public CursoProjeto adicionarCursoProjeto(CursoProjetoDTO cursoProjeto);

	public void atualizarCursoProjeto(CursoProjetoDTO cursoProjeto, Long id);

	public void removerCursoProjeto(Long id);
}
