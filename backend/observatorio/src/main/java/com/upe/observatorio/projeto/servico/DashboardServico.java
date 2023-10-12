package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import com.upe.observatorio.projeto.dominio.envelopes.DashboardResumoVO;
import com.upe.observatorio.projeto.dominio.envelopes.DashboardVO;
import com.upe.observatorio.projeto.repositorio.ProjetoRepositorio;
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

	private final ProjetoRepositorio repositorio;
	private final CampusServico campusServico;
	private final CursoServico cursoServico;
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
		dashboard.setProjectsPerModalities(obterQuantidadeDeProjetosPorModalidade());
		dashboard.setProjectsPerThematicArea(obterQuantidadeDeProjetosPorAreaTematica());

		return dashboard;
	}

	public DashboardResumoVO gerarDashboardResumo() {
		DashboardResumoVO dashboardResumo = new DashboardResumoVO();

		dashboardResumo.setTotalCourses(obterQuantidadeTotalDeCurso());
		dashboardResumo.setTotalCampuses(obterQuantidadeTotalDeCampus());
		dashboardResumo.setTotalProjects(obterQuantidadeTotalDeProjetos());

		return dashboardResumo;
	}

	public Integer obterQuantidadeTotalDeCurso() {
		List<Curso> cursos = cursoServico.listarCursos();

		return cursos.size();
	}

	public Integer obterQuantidadeTotalDeCampus() {
		List<Campus> campus = campusServico.listarCampus();

		return campus.size();
	}

	public int obterQuantidadeTotalDeProjetos() {
		return repositorio.findAll().size();
	}

	public Integer obterQuantidadeTotalDeUsuarios() {
		List<Usuario> usuarios = usuarioServico.listarUsuarios();

		return usuarios.size();
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

	public HashMap<String, Integer> obterQuantidadeDeProjetosPorModalidade() {
		HashMap<String, Integer> resultado = new HashMap<>();

		int qtdPrograma = repositorio.findAllByModalidade(ModalidadeEnum.PROGRAMA).size();
		int qtdProjeto = repositorio.findAllByModalidade(ModalidadeEnum.PROJETO).size();
		int qtdCurso = repositorio.findAllByModalidade(ModalidadeEnum.CURSO).size();
		int qtdOficina = repositorio.findAllByModalidade(ModalidadeEnum.OFICINA).size();
		int qtdEvento = repositorio.findAllByModalidade(ModalidadeEnum.EVENTO).size();

		resultado.put("Programa", qtdPrograma);
		resultado.put("Projeto", qtdProjeto);
		resultado.put("Curso", qtdCurso);
		resultado.put("Oficina", qtdOficina);
		resultado.put("Evento", qtdEvento);

		return resultado;
	}

	public HashMap<String, Integer> obterQuantidadeDeProjetosPorAreaTematica() {
		HashMap<String, Integer> resultado = new HashMap<>();

		int qtdProjetosPesquisa = repositorio.findAllByAreaTematica(AreaTematicaEnum.PESQUISA).size();
		int qtdProjetosExtensao = repositorio.findAllByAreaTematica(AreaTematicaEnum.EXTENSAO).size();
		int qtdProjetosInovacao = repositorio.findAllByAreaTematica(AreaTematicaEnum.INOVACAO).size();

		resultado.put("Pesquisa", qtdProjetosPesquisa);
		resultado.put("Extensão", qtdProjetosExtensao);
		resultado.put("Inovação", qtdProjetosInovacao);

		return resultado;
	}
}
