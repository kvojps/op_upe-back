package com.upe.observatorio.projeto.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.projeto.service.CursoProjetoService;
import com.upe.observatorio.projeto.service.ProjetoService;

@RestController
@RequestMapping("api/dashboard")
public class DashboardAPI {

	@Autowired
	ProjetoService projetoServico;
	
	@Autowired
	CursoProjetoService cursoProjetoServico;
	
	@GetMapping("/projeto/total")
	public ResponseEntity<Integer> obterQuantidadeTotalDeProjetos() {
		return ResponseEntity.ok(projetoServico.obterQuantidadeTotalDeProjetos());
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
	
}
