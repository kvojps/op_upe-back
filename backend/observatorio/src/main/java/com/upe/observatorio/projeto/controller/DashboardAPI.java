package com.upe.observatorio.projeto.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.projeto.domain.dto.DashboardDTO;
import com.upe.observatorio.projeto.service.DashboardService;

@RestController
@RequestMapping("api/dashboard")
@CrossOrigin
public class DashboardAPI {

	@Autowired
	DashboardService dashboardServico;
	
	@GetMapping
	public ResponseEntity<DashboardDTO> obterDashboard() {
		return ResponseEntity.ok(dashboardServico.gerarDashboard());
	}
	
	@GetMapping("/projeto/total")
	public ResponseEntity<Integer> obterQuantidadeTotalDeProjetos() {
		return ResponseEntity.ok(dashboardServico.obterQuantidadeTotalDeProjetos());
	}
	
	@GetMapping("/usuario/total")
	public ResponseEntity<Integer> obterQuantidadeTotalDeUsuarios() {
		return ResponseEntity.ok(dashboardServico.obterQuantidadeTotalDeUsuarios());
	}
	
	@GetMapping("/projeto/areaTematica")
	public ResponseEntity<HashMap<String, Integer>> obterQuantidadeDeProjetosPorAreaTematica() {
		return ResponseEntity.ok(dashboardServico.obterQuantidadeTotalDeProjetosPorAreaTematica());
	}
	
	@GetMapping("/projeto/modalidade")
	public ResponseEntity<HashMap<String, Integer>> obterQuantidadeDeProjetosPorModalidade() {
		return ResponseEntity.ok(dashboardServico.obterQuantidadeTotalDeProjetosPorModalidade());
	}
	
	@GetMapping("projeto/curso")
	public ResponseEntity<HashMap<String, Integer>> obterQuantidadeDeProjetosPorCurso() {
		return ResponseEntity.ok(dashboardServico.obterQuantidadeTotalDeProjetosPorCurso());
	}
	
	//projetos por campus
}
