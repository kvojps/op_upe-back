package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.domain.Campus;
import com.upe.observatorio.projeto.domain.CampusCurso;
import com.upe.observatorio.projeto.domain.Curso;
import com.upe.observatorio.projeto.domain.dto.CampusCursoDTO;
import com.upe.observatorio.projeto.repository.CampusCursoRepository;
import com.upe.observatorio.projeto.utilities.ProjetoException;

@Service
public class CampusCursoService {

	@Autowired
	private CampusCursoRepository repositorio;

	@Autowired
	private CampusService campusService;

	@Autowired
	private CursoService cursoService;

	public List<CampusCurso> listarCampusCurso() {
		return repositorio.findAll();
	}

	public Optional<CampusCurso> buscarCampusCursoPorId(Long id) {
		return repositorio.findById(id);
	}

	public CampusCurso adicionarCampusCurso(CampusCursoDTO campusCurso) throws ProjetoException {
		Optional<Campus> campusExistente = campusService.buscarCampusPorId(campusCurso.getCampusId());
		if (campusExistente.isEmpty()) {
			throw new ProjetoException("O campus informado não existe");
		}

		Optional<Curso> cursoExistente = cursoService.buscarCursoPorId(campusCurso.getCursoId());
		if (cursoExistente.isEmpty()) {
			throw new ProjetoException("O curso informado não existe");
		}

		CampusCurso campusCursoSalvar = new CampusCurso();
		campusCursoSalvar.setCampus(campusExistente.get());
		campusCursoSalvar.setCurso(cursoExistente.get());

		return repositorio.save(campusCursoSalvar);

	}

	public void removerCampusCurso(Long id) throws ProjetoException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ProjetoException("Não existe um relacionamento entre campus e curso associado a este id");
		}
		
		repositorio.deleteById(id);

	}

}
