package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.CampusCurso;
import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.dto.CampusCursoDTO;
import com.upe.observatorio.projeto.repositorio.CampusCursoRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CampusCursoServico {

	private final CampusCursoRepositorio repositorio;
	private final CampusServico campusServico;
	private final CursoServico cursoServico;

	public CampusCurso adicionarCampusCurso(CampusCursoDTO campusCurso) throws ObservatorioExcecao {
		Campus campusExistente = campusServico.buscarCampusPorId(campusCurso.getCampusId());
		Curso cursoExistente = cursoServico.buscarCursoPorId(campusCurso.getCursoId());
		

		repositorio.findByCampusAndCurso(campusExistente, cursoExistente).orElseThrow(() ->
				new ObservatorioExcecao("Já existe um relacionamento criado entre o campuse e o curso informado"));
		
		CampusCurso campusCursoSalvar = new CampusCurso();
		campusCursoSalvar.setCampus(campusExistente);
		campusCursoSalvar.setCurso(cursoExistente);

		return repositorio.save(campusCursoSalvar);
	}

	public void removerCampusCurso(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um relacionamento entre campus e curso associado a este id");
		}

		repositorio.deleteById(id);
	}
}
