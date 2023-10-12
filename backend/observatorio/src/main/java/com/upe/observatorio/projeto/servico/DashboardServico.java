package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import com.upe.observatorio.projeto.dominio.envelopes.DashboardResumoVO;
import com.upe.observatorio.projeto.dominio.envelopes.DashboardVO;
import com.upe.observatorio.projeto.repositorio.CampusRepositorio;
import com.upe.observatorio.projeto.repositorio.CursoRepositorio;
import com.upe.observatorio.projeto.repositorio.ProjetoRepositorio;
import com.upe.observatorio.usuario.repositorio.UsuarioRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class DashboardServico {

	private final CampusRepositorio campusRepositorio;
	private final CursoRepositorio cursoRepositorio;
	private final ProjetoRepositorio projetoRepositorio;
	private final UsuarioRepositorio usuarioRepositorio;

	private final CampusServico campusServico;
	private final CursoProjetoServico cursoProjetoServico;

	public DashboardVO gerarDashboard() throws ObservatorioExcecao {
		DashboardVO dashboard = new DashboardVO();

		dashboard.setTotalCampuses(campusRepositorio.count());
		dashboard.setTotalCourses(cursoRepositorio.count());
		dashboard.setTotalProjects(projetoRepositorio.count());
		dashboard.setTotalUsers(usuarioRepositorio.count());
		dashboard.setProjectsPerCourses(cursoProjetoServico.obterQuantidadeDeProjetosPorCurso());
		dashboard.setProjectsPerCampuses(campusServico.obterQuantidadeTotalDeProjetosPorCampus());
		dashboard.setProjectsPerModalities(obterQuantidadeDeProjetosPorModalidade());
		dashboard.setProjectsPerThematicArea(obterQuantidadeDeProjetosPorAreaTematica());

		return dashboard;
	}

	public DashboardResumoVO gerarDashboardResumo() {
		DashboardResumoVO dashboardResumo = new DashboardResumoVO();

		dashboardResumo.setTotalCampuses(campusRepositorio.count());
		dashboardResumo.setTotalCourses(cursoRepositorio.count());
		dashboardResumo.setTotalProjects(projetoRepositorio.count());

		return dashboardResumo;
	}

	public HashMap<String, Long> obterQuantidadeDeProjetosPorModalidade() {
		HashMap<String, Long> resultado = new HashMap<>();

		resultado.put("Programa", projetoRepositorio.countByModalidade(ModalidadeEnum.PROGRAMA));
		resultado.put("Projeto", projetoRepositorio.countByModalidade(ModalidadeEnum.PROJETO));
		resultado.put("Curso", projetoRepositorio.countByModalidade(ModalidadeEnum.CURSO));
		resultado.put("Oficina", projetoRepositorio.countByModalidade(ModalidadeEnum.OFICINA));
		resultado.put("Evento", projetoRepositorio.countByModalidade(ModalidadeEnum.EVENTO));

		return resultado;
	}

	public HashMap<String, Long> obterQuantidadeDeProjetosPorAreaTematica() {
		HashMap<String, Long> resultado = new HashMap<>();

		resultado.put("Pesquisa", projetoRepositorio.countByAreaTematica(AreaTematicaEnum.PESQUISA));
		resultado.put("Extensão", projetoRepositorio.countByAreaTematica(AreaTematicaEnum.EXTENSAO));
		resultado.put("Inovação", projetoRepositorio.countByAreaTematica(AreaTematicaEnum.INOVACAO));

		return resultado;
	}
}
