package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.dto.CursoDTO;
import com.upe.observatorio.projeto.repositorio.CourseRepository;
import com.upe.observatorio.utils.ObservatorioExcecao;
import com.upe.observatorio.utils.ProjectResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoServico {

	private final CourseRepository repositorio;

	public Curso adicionarCurso(CursoDTO curso) {
		Curso cursoSalvar = new Curso();
		BeanUtils.copyProperties(curso, cursoSalvar);

		return repositorio.save(cursoSalvar);
	}

	public List<Curso> listarCursos() {
		return repositorio.findAll();
	}

	public Curso buscarCursoPorId(Long id) {
		return repositorio.findById(id).orElseThrow(() ->
				new ProjectResourceNotFoundException("Curso not found"));
	}

	public void atualizarCurso(CursoDTO curso, Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ProjectResourceNotFoundException("Curso not found");
		}
		
		Curso cursoExistente = repositorio.findById(id).get();
		BeanUtils.copyProperties(curso, cursoExistente);
		
		repositorio.save(cursoExistente);
	}

	public void removerCurso(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ProjectResourceNotFoundException("Curso not found");
		}
		
		repositorio.deleteById(id);
	}
}
