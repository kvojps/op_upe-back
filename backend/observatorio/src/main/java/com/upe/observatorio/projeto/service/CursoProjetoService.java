package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Optional;

import com.upe.observatorio.projeto.domain.CursoProjeto;
import com.upe.observatorio.projeto.domain.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.utilities.ProjetoException;

public interface CursoProjetoService {
	
	public List<CursoProjeto> listarCursoProjetos();

	public Optional<CursoProjeto> buscarCursoProjetoPorId(Long id);

	public CursoProjeto adicionarCursoProjeto(CursoProjetoDTO cursoProjeto) throws ProjetoException;

	public void atualizarCursoProjeto(CursoProjetoDTO cursoProjeto, Long id) throws ProjetoException;

	public void removerCursoProjeto(Long id) throws ProjetoException;
}
