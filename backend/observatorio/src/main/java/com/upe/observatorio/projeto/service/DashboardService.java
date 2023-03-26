package com.upe.observatorio.projeto.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.domain.Campus;
import com.upe.observatorio.projeto.domain.Curso;
import com.upe.observatorio.projeto.domain.dto.DashboardDTO;
import com.upe.observatorio.usuario.domain.Usuario;
import com.upe.observatorio.usuario.service.UsuarioService;

@Service
public class DashboardService {
	
	@Autowired
	CampusService campusServico;
	
	@Autowired
	CursoService cursoServico;
	
	@Autowired
	ProjetoService projetoServico;
	
	@Autowired
	UsuarioService usuarioServico;
	
	@Autowired
	CursoProjetoService cursoProjetoServico;
	
	public DashboardDTO gerarDashboard() {
		DashboardDTO dashboard = new DashboardDTO();
		
		dashboard.setTotalCourses(obterQuantidadeTotalDeCurso());
		dashboard.setTotalCampuses(obterQuantidadeTotalDeCampus());
		dashboard.setTotalProjects(obterQuantidadeTotalDeProjetos());
		dashboard.setTotalUsers(obterQuantidadeTotalDeUsuarios());
		dashboard.setCourses(obterQuantidadeTotalDeProjetosPorCurso());
		dashboard.setCampuses(obterQuantidadeTotalDeProjetosPorCampus());
		dashboard.setModalities(obterQuantidadeTotalDeProjetosPorModalidade());
		dashboard.setThematicArea(obterQuantidadeTotalDeProjetosPorAreaTematica());
		
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
