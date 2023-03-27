package com.upe.observatorio.projeto.servico;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.envelopes.DashboardVO;
import com.upe.observatorio.usuario.dominio.Usuario;
import com.upe.observatorio.usuario.servico.UsuarioServico;

@Service
public class DashboardServico {
	
	@Autowired
	CampusServico campusServico;
	
	@Autowired
	CursoServico cursoServico;
	
	@Autowired
	ProjetoServico projetoServico;
	
	@Autowired
	UsuarioServico usuarioServico;
	
	@Autowired
	CursoProjetoServico cursoProjetoServico;
	
	public DashboardVO gerarDashboard() {
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
	
	public Integer obterQuantidadeTotalDeUsuarios() {
		List<Usuario> usuarios = usuarioServico.listarUsuarios();
		
		return usuarios.size();
	}
	
	public HashMap<String, Integer> obterQuantidadeTotalDeProjetosPorModalidade() {
		return projetoServico.obterQuantidadeDeProjetosPorModalidade();
	}
	
	public HashMap<String, Integer> obterQuantidadeTotalDeProjetosPorAreaTematica() {
		return projetoServico.obterQuantidadeDeProjetosPorAreaTematica();
	}
	
	public HashMap<String, Integer> obterQuantidadeTotalDeProjetosPorCurso() {
		return cursoProjetoServico.obterQuantidadeDeProjetosPorCurso();
	}
	
	public HashMap<String, Integer> obterQuantidadeTotalDeProjetosPorCampus() {
		return null;
	}
}
