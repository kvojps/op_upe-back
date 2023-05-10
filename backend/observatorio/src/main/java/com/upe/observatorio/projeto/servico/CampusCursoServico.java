package com.upe.observatorio.projeto.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.CampusCurso;
import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.dto.CampusCursoDTO;
import com.upe.observatorio.projeto.repositorio.CampusCursoRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;

@Service
public class CampusCursoServico {

	@Autowired
	private CampusCursoRepositorio repositorio;

	@Autowired
	private CampusServico campusService;

	@Autowired
	private CursoServico cursoService;

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
		Optional<Campus> campusExistente = campusService.buscarCampusPorId(campusCurso.getCampusId());
		if (campusExistente.isEmpty()) {
			throw new ObservatorioExcecao("O campus informado não existe");
		}

		Optional<Curso> cursoExistente = cursoService.buscarCursoPorId(campusCurso.getCursoId());
		if (cursoExistente.isEmpty()) {
			throw new ObservatorioExcecao("O curso informado não existe");
		}
		
		Optional<CampusCurso> campusCursoExistente = repositorio.findByCampusAndCurso(campusExistente.get(), cursoExistente.get());
		if (campusCursoExistente.isPresent()) {
			throw new ObservatorioExcecao("Já existe um relacionamento criado entre o campus e o curso informado");
		}
		
		CampusCurso campusCursoSalvar = new CampusCurso();
		campusCursoSalvar.setCampus(campusExistente.get());
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
