package com.upe.observatorio.projeto.servico;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.dto.CursoDTO;
import com.upe.observatorio.projeto.repositorio.CursoRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;

@Service
public class CursoServico {
	
	@Autowired
	private CursoRepositorio repositorio;

	public List<Curso> listarCursos() {
		return repositorio.findAll();
	}

	public Optional<Curso> buscarCursoPorId(Long id) {
		return repositorio.findById(id);
	}

	public Curso adicionarCurso(CursoDTO curso) throws ObservatorioExcecao {
		ModelMapper modelMapper = new ModelMapper();
		Curso cursoSalvar = modelMapper.map(curso, Curso.class);

		return repositorio.save(cursoSalvar);
	}

	public void atualizarCurso(CursoDTO curso, Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um curso associado a este id");
		}
		
		Curso cursoExistente = repositorio.findById(id).get();
		if (!cursoExistente.getNome().equals(curso.getNome())) {
			cursoExistente.setNome(curso.getNome());
		}
		
		repositorio.save(cursoExistente);
		
	}

	public void removerCurso(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um curso associado a este id");
		}
		
		repositorio.deleteById(id);
		
	}

	public Page<Curso> filtrarCursoPorNome(String nome, Pageable pageable) {
		return repositorio.findAllByNomeContainingIgnoreCase(nome, pageable);
	}

}
