package com.upe.observatorio.project.service;

import com.upe.observatorio.project.model.Campus;
import com.upe.observatorio.project.model.Curso;
import com.upe.observatorio.project.model.CursoProjeto;
import com.upe.observatorio.project.model.enums.ThematicAreaEnum;
import com.upe.observatorio.project.model.enums.ModalityEnum;
import com.upe.observatorio.project.model.vos.DashboardOverviewVO;
import com.upe.observatorio.project.model.vos.DashboardVO;
import com.upe.observatorio.project.repository.CampusRepository;
import com.upe.observatorio.project.repository.CourseProjectRepository;
import com.upe.observatorio.project.repository.CourseRepository;
import com.upe.observatorio.project.repository.ProjectRepository;
import com.upe.observatorio.user.repositorio.UsuarioRepositorio;
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

	public DashboardOverviewVO getDashboardOverview() {
		DashboardOverviewVO dashboardOverview = new DashboardOverviewVO();

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

		result.put("Programa", projectRepository.countByModalidade(ModalityEnum.PROGRAMA));
		result.put("Projeto", projectRepository.countByModalidade(ModalityEnum.PROJETO));
		result.put("Curso", projectRepository.countByModalidade(ModalityEnum.CURSO));
		result.put("Oficina", projectRepository.countByModalidade(ModalityEnum.OFICINA));
		result.put("Evento", projectRepository.countByModalidade(ModalityEnum.EVENTO));

		return result;
	}

	private HashMap<String, Long> countProjectsPerThematicaArea() {
		HashMap<String, Long> result = new HashMap<>();

		result.put("Pesquisa", projectRepository.countByAreaTematica(ThematicAreaEnum.PESQUISA));
		result.put("Extensão", projectRepository.countByAreaTematica(ThematicAreaEnum.EXTENSAO));
		result.put("Inovação", projectRepository.countByAreaTematica(ThematicAreaEnum.INOVACAO));

		return result;
	}
}
