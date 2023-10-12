package com.upe.observatorio.projeto.servico;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.CampusCurso;
import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.dto.CampusCursoDTO;
import com.upe.observatorio.projeto.repositorio.CampusCursoRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;

@Service
@RequiredArgsConstructor
public class CampusCursoServico {

	private final CampusCursoRepositorio repositorio;
	private final CampusServico campusServico;
	private final CursoServico cursoServico;

	public List<CampusCurso> listarCampusCurso() {
		return repositorio.findAll();
	}

	public Optional<CampusCurso> buscarCampusCursoPorId(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um CampusCurso associado a este id!");
		}
		return repositorio.findById(id);
	}

	public CampusCurso adicionarCampusCurso(CampusCursoDTO campusCurso) throws ObservatorioExcecao {
		Campus campusExistente = campusServico.buscarCampusPorId(campusCurso.getCampusId());

		Optional<Curso> cursoExistente = cursoServico.buscarCursoPorId(campusCurso.getCursoId());
		if (cursoExistente.isEmpty()) {
			throw new ObservatorioExcecao("O curso informado não existe");
		}
		
		Optional<CampusCurso> campusCursoExistente = repositorio.findByCampusAndCurso(campusExistente, cursoExistente.get());
		if (campusCursoExistente.isPresent()) {
			throw new ObservatorioExcecao("Já existe um relacionamento criado entre o campus e o curso informado");
		}
		
		CampusCurso campusCursoSalvar = new CampusCurso();
		campusCursoSalvar.setCampus(campusExistente);
		campusCursoSalvar.setCurso(cursoExistente.get());

		return repositorio.save(campusCursoSalvar);
	}

	public void removerCampusCurso(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um relacionamento entre campus e curso associado a este id");
		}

		repositorio.deleteById(id);
	}

}
