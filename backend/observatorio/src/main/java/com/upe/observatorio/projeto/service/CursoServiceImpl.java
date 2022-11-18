package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.domain.Curso;
import com.upe.observatorio.projeto.domain.dto.CursoDTO;
import com.upe.observatorio.projeto.repository.CursoRepository;
import com.upe.observatorio.projeto.utilities.ProjetoException;

@Service
public class CursoServiceImpl implements CursoService {
	
	@Autowired
	private CursoRepository repositorio;

	@Override
	public List<Curso> listarCursos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Curso> buscarCursoPorId(Long id) {
		return repositorio.findById(id);
	}

	@Override
	public Curso adicionarCurso(CursoDTO curso) throws ProjetoException {
		ModelMapper modelMapper = new ModelMapper();
		Curso cursoSalvar = modelMapper.map(curso, Curso.class);

		return repositorio.save(cursoSalvar);
	}

	@Override
	public void atualizarCurso(CursoDTO curso, Long id) throws ProjetoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removerCurso(Long id) throws ProjetoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> filtrarCursoPorNome(String nome, int pag, int tamanho) {
		// TODO Auto-generated method stub
		return null;
	}

}
