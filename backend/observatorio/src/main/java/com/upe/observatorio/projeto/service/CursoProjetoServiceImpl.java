package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.domain.Curso;
import com.upe.observatorio.projeto.domain.CursoProjeto;
import com.upe.observatorio.projeto.domain.Projeto;
import com.upe.observatorio.projeto.domain.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.repository.CursoProjetoRepository;
import com.upe.observatorio.projeto.utilities.ProjetoException;

@Service
public class CursoProjetoServiceImpl implements CursoProjetoService {
	
	@Autowired
	private CursoProjetoRepository repositorio;
	
	@Autowired
	private CursoService cursoServico;
	
	@Autowired
	private ProjetoService projetoServico;
	
	@Override
	public List<CursoProjeto> listarCursoProjetos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<CursoProjeto> buscarCursoProjetoPorId(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public CursoProjeto adicionarCursoProjeto(CursoProjetoDTO cursoProjeto) throws ProjetoException {

		Optional<Curso> cursoExistente = cursoServico.buscarCursoPorId(cursoProjeto.getCursoId());
		if (cursoExistente.isEmpty()) {
			throw new ProjetoException("O curso informado não existe");
		}
		
		Optional<Projeto> projetoExistente = projetoServico.buscarProjetoPorId(cursoProjeto.getProjetoId());
		if (projetoExistente.isEmpty()) {
			throw new ProjetoException("O projeto informado não existe");
		}
		
		
		CursoProjeto cursoProjetoSalvar = new CursoProjeto();
		cursoProjetoSalvar.setCurso(cursoExistente.get());
		cursoProjetoSalvar.setProjeto(projetoExistente.get());
		
		return repositorio.save(cursoProjetoSalvar);
	}

	@Override
	public void atualizarCursoProjeto(CursoProjetoDTO cursoProjeto, Long id) throws ProjetoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removerCursoProjeto(Long id) throws ProjetoException {
		// TODO Auto-generated method stub
		
	}

}
