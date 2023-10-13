package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.dto.CursoDTO;
import com.upe.observatorio.projeto.repositorio.CursoRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoServico {

	private final CursoRepositorio repositorio;

	public Curso adicionarCurso(CursoDTO curso) {
		Curso cursoSalvar = new Curso();
		BeanUtils.copyProperties(curso, cursoSalvar);

		return repositorio.save(cursoSalvar);
	}

	public List<Curso> listarCursos() {
		return repositorio.findAll();
	}

	public Curso buscarCursoPorId(Long id) throws ObservatorioExcecao {
		return repositorio.findById(id).orElseThrow(() ->
				new ObservatorioExcecao("Não existe um curso associado a este id: " + id));
	}

	public void atualizarCurso(CursoDTO curso, Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um curso associado a este id");
		}
		
		Curso cursoExistente = repositorio.findById(id).get();
		BeanUtils.copyProperties(curso, cursoExistente);
		
		repositorio.save(cursoExistente);
	}

	public void removerCurso(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um curso associado a este id");
		}
		
		repositorio.deleteById(id);
	}
}
