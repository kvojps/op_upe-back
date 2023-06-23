package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.envelopes.DashboardVO;
import com.upe.observatorio.usuario.dominio.Usuario;
import com.upe.observatorio.usuario.servico.UsuarioServico;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServico {

	private final CampusServico campusServico;
	private final CursoServico cursoServico;
	private final ProjetoServico projetoServico;
	private final UsuarioServico usuarioServico;
	private final CursoProjetoServico cursoProjetoServico;

	public DashboardVO gerarDashboard() throws ObservatorioExcecao {
		DashboardVO dashboard = new DashboardVO();

		dashboard.setTotalCourses(obterQuantidadeTotalDeCurso());
		dashboard.setTotalCampuses(obterQuantidadeTotalDeCampus());
		dashboard.setTotalProjects(obterQuantidadeTotalDeProjetos());
		dashboard.setTotalUsers(obterQuantidadeTotalDeUsuarios());
		dashboard.setProjectsPerCourses(obterQuantidadeTotalDeProjetosPorCurso());
		dashboard.setProjectsPerCampuses(obterQuantidadeTotalDeProjetosPorCampus());
		dashboard.setProjectsPerModalities(obterQuantidadeTotalDeProjetosPorModalidade());
		dashboard.setProjectsPerThematicArea(obterQuantidadeTotalDeProjetosPorAreaTematica());

		return dashboard;
	}

	public Integer obterQuantidadeTotalDeCampus() {
		List<Campus> campus = campusServico.listarCampus();

		return campus.size();
	}

	public Integer obterQuantidadeTotalDeCurso() {
		List<Curso> cursos = cursoServico.listarCursos();

		return cursos.size();
	}

	public Integer obterQuantidadeTotalDeProjetos() {
		return projetoServico.obterQuantidadeTotalDeProjetos();
	}

	public HashMap<String, Integer> obterQuantidadeTotalDeProjetosPorModalidade() {
		return projetoServico.obterQuantidadeDeProjetosPorModalidade();
	}

	public HashMap<String, Integer> obterQuantidadeTotalDeProjetosPorAreaTematica() {
		return projetoServico.obterQuantidadeDeProjetosPorAreaTematica();
	}

	public HashMap<String, Integer> obterQuantidadeTotalDeProjetosPorCurso() throws ObservatorioExcecao {
		return cursoProjetoServico.obterQuantidadeDeProjetosPorCurso();
	}

	public HashMap<String, Integer> obterQuantidadeTotalDeProjetosPorCampus() {
		HashMap<String, Integer> resultado = new HashMap<>();
		List<Campus> campi = campusServico.listarCampus();

		for (Campus campus : campi) {
			resultado.put(campus.getNome(), campus.getProjetos().size());
		}

		return resultado;
	}

	public Integer obterQuantidadeTotalDeUsuarios() {
		List<Usuario> usuarios = usuarioServico.listarUsuarios();

		return usuarios.size();
	}

}
