package com.upe.observatorio.projeto.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.projeto.service.CursoProjetoService;
import com.upe.observatorio.projeto.service.ProjetoService;
import com.upe.observatorio.usuario.domain.Usuario;
import com.upe.observatorio.usuario.service.UsuarioService;

@RestController
@RequestMapping("api/dashboard")
public class DashboardAPI {

	@Autowired
	ProjetoService projetoServico;
	
	@Autowired
	UsuarioService usuarioServico;
	
	@Autowired
	CursoProjetoService cursoProjetoServico;
	
	@GetMapping("/projeto/total")
	public ResponseEntity<Integer> obterQuantidadeTotalDeProjetos() {
		return ResponseEntity.ok(projetoServico.obterQuantidadeTotalDeProjetos());
	}
	
	@GetMapping("/usuario/total")
	public ResponseEntity<Integer> obterQuantidadeTotalDeUsuarios() {
		List<Usuario> usuarios = usuarioServico.listarUsuarios();
		
		return ResponseEntity.ok(usuarios.size());
	}
	
	@GetMapping("/projeto/areaTematica")
	public ResponseEntity<HashMap<String, Integer>> obterQuantidadeDeProjetosPorAreaTematica() {
		return ResponseEntity.ok(projetoServico.obterQuantidadeDeProjetosPorAreaTematica());
	}
	
	@GetMapping("/projeto/modalidade")
	public ResponseEntity<HashMap<String, Integer>> obterQuantidadeDeProjetosPorModalidade() {
		return ResponseEntity.ok(projetoServico.obterQuantidadeDeProjetosPorModalidade());
	}
	
	@GetMapping("projeto/curso")
	public ResponseEntity<HashMap<String, Integer>> obterQuantidadeDeProjetosPorCurso() {
		return ResponseEntity.ok(cursoProjetoServico.obterQuantidadeDeProjetosPorCurso());
	}
	
	//projetos por campus
	
}
