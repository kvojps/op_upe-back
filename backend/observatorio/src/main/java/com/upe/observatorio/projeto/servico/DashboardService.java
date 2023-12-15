package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.CursoProjeto;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import com.upe.observatorio.projeto.dominio.envelopes.DashboardResumoVO;
import com.upe.observatorio.projeto.dominio.envelopes.DashboardVO;
import com.upe.observatorio.projeto.repository.CampusRepository;
import com.upe.observatorio.projeto.repository.CourseProjectRepository;
import com.upe.observatorio.projeto.repository.CourseRepository;
import com.upe.observatorio.projeto.repository.ProjectRepository;
import com.upe.observatorio.usuario.repositorio.UsuarioRepositorio;
import com.upe.observatorio.utils.ProjectResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

	private final CampusRepository campusRepository;
	private final CourseRepository courseRepository;
	private final ProjectRepository projectRepository;
	private final CourseProjectRepository courseProjectRepository;
	private final UsuarioRepositorio userRepository;

	public DashboardVO getDashboard() {
		DashboardVO dashboard = new DashboardVO();

		dashboard.setTotalCampuses(campusRepository.count());
		dashboard.setTotalCourses(courseRepository.count());
		dashboard.setTotalProjects(projectRepository.count());
		dashboard.setTotalUsers(userRepository.count());
		dashboard.setProjectsPerCourses(countProjectsPerCourse());
		dashboard.setProjectsPerCampuses(countProjectsPerCampus());
		dashboard.setProjectsPerModalities(countProjectsPerModality());
		dashboard.setProjectsPerThematicArea(countProjectsPerThematicaArea());

		return dashboard;
	}

	public DashboardResumoVO getDashboardOverview() {
		DashboardResumoVO dashboardOverview = new DashboardResumoVO();

		dashboardOverview.setTotalCampuses(campusRepository.count());
		dashboardOverview.setTotalCourses(courseRepository.count());
		dashboardOverview.setTotalProjects(projectRepository.count());

		return dashboardOverview;
	}

	private HashMap<String, Integer> countProjectsPerCourse() {
		HashMap<String, Integer> result = new HashMap<>();
		List<CursoProjeto> courseProjects = courseProjectRepository.findAll();

		for (CursoProjeto courseProject : courseProjects) {
			Curso curso = courseRepository.findById(courseProject.getCurso().getId()).orElseThrow(() ->
					new ProjectResourceNotFoundException("Course not found"));
			if (result.containsKey(curso.getNome())) {
				Integer projectsAmount = result.get(curso.getNome());
				result.put(curso.getNome(), projectsAmount + 1);
			} else {
				result.put(curso.getNome(), 1);
			}
		}

		return result;
	}

	private HashMap<String, Integer> countProjectsPerCampus() {
		HashMap<String, Integer> result = new HashMap<>();
		List<Campus> campuses = campusRepository.findAll();

		for (Campus campus : campuses) {
			result.put(campus.getNome(), campus.getProjetos().size());
		}

		return result;
	}

	private HashMap<String, Long> countProjectsPerModality() {
		HashMap<String, Long> result = new HashMap<>();

		result.put("Programa", projectRepository.countByModalidade(ModalidadeEnum.PROGRAMA));
		result.put("Projeto", projectRepository.countByModalidade(ModalidadeEnum.PROJETO));
		result.put("Curso", projectRepository.countByModalidade(ModalidadeEnum.CURSO));
		result.put("Oficina", projectRepository.countByModalidade(ModalidadeEnum.OFICINA));
		result.put("Evento", projectRepository.countByModalidade(ModalidadeEnum.EVENTO));

		return result;
	}

	private HashMap<String, Long> countProjectsPerThematicaArea() {
		HashMap<String, Long> result = new HashMap<>();

		result.put("Pesquisa", projectRepository.countByAreaTematica(AreaTematicaEnum.PESQUISA));
		result.put("Extensão", projectRepository.countByAreaTematica(AreaTematicaEnum.EXTENSAO));
		result.put("Inovação", projectRepository.countByAreaTematica(AreaTematicaEnum.INOVACAO));

		return result;
	}
}
