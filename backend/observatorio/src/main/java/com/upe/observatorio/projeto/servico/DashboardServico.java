package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.CursoProjeto;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import com.upe.observatorio.projeto.dominio.envelopes.DashboardResumoVO;
import com.upe.observatorio.projeto.dominio.envelopes.DashboardVO;
import com.upe.observatorio.projeto.repositorio.CampusRepository;
import com.upe.observatorio.projeto.repositorio.CourseProjectRepository;
import com.upe.observatorio.projeto.repositorio.CourseRepository;
import com.upe.observatorio.projeto.repositorio.ProjectRepository;
import com.upe.observatorio.usuario.repositorio.UsuarioRepositorio;
import com.upe.observatorio.utils.ProjectResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServico {

	private final CampusRepository campusRepository;
	private final CourseRepository courseRepository;
	private final ProjectRepository projectRepository;
	private final CourseProjectRepository courseProjectRepository;
	private final UsuarioRepositorio usuarioRepositorio;

	public DashboardVO gerarDashboard() {
		DashboardVO dashboard = new DashboardVO();

		dashboard.setTotalCampuses(campusRepository.count());
		dashboard.setTotalCourses(courseRepository.count());
		dashboard.setTotalProjects(projectRepository.count());
		dashboard.setTotalUsers(usuarioRepositorio.count());
		dashboard.setProjectsPerCourses(obterTotalProjetosPorCurso());
		dashboard.setProjectsPerCampuses(obterTotalProjetosPorCampus());
		dashboard.setProjectsPerModalities(obterTotalProjetosPorModalidade());
		dashboard.setProjectsPerThematicArea(obterTotalProjetosPorAreaTematica());

		return dashboard;
	}

	public DashboardResumoVO gerarDashboardResumo() {
		DashboardResumoVO dashboardResumo = new DashboardResumoVO();

		dashboardResumo.setTotalCampuses(campusRepository.count());
		dashboardResumo.setTotalCourses(courseRepository.count());
		dashboardResumo.setTotalProjects(projectRepository.count());

		return dashboardResumo;
	}

	private HashMap<String, Integer> obterTotalProjetosPorCurso() {
		HashMap<String, Integer> resultado = new HashMap<>();
		List<CursoProjeto> cursoProjetos = courseProjectRepository.findAll();

		for (CursoProjeto cursoProjeto : cursoProjetos) {
			Curso curso = courseRepository.findById(cursoProjeto.getCurso().getId()).orElseThrow(() ->
					new ProjectResourceNotFoundException("Não existe um curso associado a este id"));
			if (resultado.containsKey(curso.getNome())) {
				Integer qtdProjetos = resultado.get(curso.getNome());
				resultado.put(curso.getNome(), qtdProjetos + 1);
			} else {
				resultado.put(curso.getNome(), 1);
			}
		}

		return resultado;
	}

	private HashMap<String, Integer> obterTotalProjetosPorCampus() {
		HashMap<String, Integer> resultado = new HashMap<>();
		List<Campus> campi = campusRepository.findAll();

		for (Campus campus : campi) {
			resultado.put(campus.getNome(), campus.getProjetos().size());
		}

		return resultado;
	}

	private HashMap<String, Long> obterTotalProjetosPorModalidade() {
		HashMap<String, Long> resultado = new HashMap<>();

		resultado.put("Programa", projectRepository.countByModalidade(ModalidadeEnum.PROGRAMA));
		resultado.put("Projeto", projectRepository.countByModalidade(ModalidadeEnum.PROJETO));
		resultado.put("Curso", projectRepository.countByModalidade(ModalidadeEnum.CURSO));
		resultado.put("Oficina", projectRepository.countByModalidade(ModalidadeEnum.OFICINA));
		resultado.put("Evento", projectRepository.countByModalidade(ModalidadeEnum.EVENTO));

		return resultado;
	}

	private HashMap<String, Long> obterTotalProjetosPorAreaTematica() {
		HashMap<String, Long> resultado = new HashMap<>();

		resultado.put("Pesquisa", projectRepository.countByAreaTematica(AreaTematicaEnum.PESQUISA));
		resultado.put("Extensão", projectRepository.countByAreaTematica(AreaTematicaEnum.EXTENSAO));
		resultado.put("Inovação", projectRepository.countByAreaTematica(AreaTematicaEnum.INOVACAO));

		return resultado;
	}
}
