package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.CursoProjeto;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import com.upe.observatorio.projeto.dominio.envelopes.DashboardResumoVO;
import com.upe.observatorio.projeto.dominio.envelopes.DashboardVO;
import com.upe.observatorio.projeto.repositorio.CampusRepositorio;
import com.upe.observatorio.projeto.repositorio.CursoProjetoRepositorio;
import com.upe.observatorio.projeto.repositorio.CursoRepositorio;
import com.upe.observatorio.projeto.repositorio.ProjetoRepositorio;
import com.upe.observatorio.usuario.repositorio.UsuarioRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServico {

	private final CampusRepositorio campusRepositorio;
	private final CursoRepositorio cursoRepositorio;
	private final ProjetoRepositorio projetoRepositorio;
	private final CursoProjetoRepositorio cursoProjetoRepositorio;
	private final UsuarioRepositorio usuarioRepositorio;

	public DashboardVO gerarDashboard() throws ObservatorioExcecao {
		DashboardVO dashboard = new DashboardVO();

		dashboard.setTotalCampuses(campusRepositorio.count());
		dashboard.setTotalCourses(cursoRepositorio.count());
		dashboard.setTotalProjects(projetoRepositorio.count());
		dashboard.setTotalUsers(usuarioRepositorio.count());
		dashboard.setProjectsPerCourses(obterTotalProjetosPorCurso());
		dashboard.setProjectsPerCampuses(obterTotalProjetosPorCampus());
		dashboard.setProjectsPerModalities(obterTotalProjetosPorModalidade());
		dashboard.setProjectsPerThematicArea(obterTotalProjetosPorAreaTematica());

		return dashboard;
	}

	public DashboardResumoVO gerarDashboardResumo() {
		DashboardResumoVO dashboardResumo = new DashboardResumoVO();

		dashboardResumo.setTotalCampuses(campusRepositorio.count());
		dashboardResumo.setTotalCourses(cursoRepositorio.count());
		dashboardResumo.setTotalProjects(projetoRepositorio.count());

		return dashboardResumo;
	}

	private HashMap<String, Integer> obterTotalProjetosPorCurso() throws ObservatorioExcecao {
		HashMap<String, Integer> resultado = new HashMap<>();
		List<CursoProjeto> cursoProjetos = cursoProjetoRepositorio.findAll();

		for (CursoProjeto cursoProjeto : cursoProjetos) {
			Curso curso = cursoRepositorio.findById(cursoProjeto.getCurso().getId()).orElseThrow(() ->
					new ObservatorioExcecao("Não existe um curso associado a este id"));
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
		List<Campus> campi = campusRepositorio.findAll();

		for (Campus campus : campi) {
			resultado.put(campus.getNome(), campus.getProjetos().size());
		}

		return resultado;
	}

	private HashMap<String, Long> obterTotalProjetosPorModalidade() {
		HashMap<String, Long> resultado = new HashMap<>();

		resultado.put("Programa", projetoRepositorio.countByModalidade(ModalidadeEnum.PROGRAMA));
		resultado.put("Projeto", projetoRepositorio.countByModalidade(ModalidadeEnum.PROJETO));
		resultado.put("Curso", projetoRepositorio.countByModalidade(ModalidadeEnum.CURSO));
		resultado.put("Oficina", projetoRepositorio.countByModalidade(ModalidadeEnum.OFICINA));
		resultado.put("Evento", projetoRepositorio.countByModalidade(ModalidadeEnum.EVENTO));

		return resultado;
	}

	private HashMap<String, Long> obterTotalProjetosPorAreaTematica() {
		HashMap<String, Long> resultado = new HashMap<>();

		resultado.put("Pesquisa", projetoRepositorio.countByAreaTematica(AreaTematicaEnum.PESQUISA));
		resultado.put("Extensão", projetoRepositorio.countByAreaTematica(AreaTematicaEnum.EXTENSAO));
		resultado.put("Inovação", projetoRepositorio.countByAreaTematica(AreaTematicaEnum.INOVACAO));

		return resultado;
	}
}
