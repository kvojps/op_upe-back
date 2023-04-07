package com.upe.observatorio.projeto.controlador;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.projeto.dominio.envelopes.DashboardVO;
import com.upe.observatorio.projeto.servico.DashboardServico;

@RestController
@RequestMapping("api/dashboard")
@CrossOrigin
public class DashboardAPI {

	@Autowired
	DashboardServico servico;
	
	@GetMapping
	public ResponseEntity<DashboardVO> obterDashboard() {
		return ResponseEntity.ok(servico.gerarDashboard());
	}
	
	@GetMapping("/projeto/total")
	public ResponseEntity<Integer> obterQuantidadeTotalDeProjetos() {
		return ResponseEntity.ok(servico.obterQuantidadeTotalDeProjetos());
	}
	
	@GetMapping("/projeto/areaTematica")
	public ResponseEntity<HashMap<String, Integer>> obterQuantidadeDeProjetosPorAreaTematica() {
		return ResponseEntity.ok(servico.obterQuantidadeTotalDeProjetosPorAreaTematica());
	}
	
	@GetMapping("/projeto/modalidade")
	public ResponseEntity<HashMap<String, Integer>> obterQuantidadeDeProjetosPorModalidade() {
		return ResponseEntity.ok(servico.obterQuantidadeTotalDeProjetosPorModalidade());
	}
	
	@GetMapping("projeto/curso")
	public ResponseEntity<HashMap<String, Integer>> obterQuantidadeDeProjetosPorCurso() {
		return ResponseEntity.ok(servico.obterQuantidadeTotalDeProjetosPorCurso());
	}
	
	@GetMapping("projeto/campus")
	public ResponseEntity<HashMap<String, Integer>> obterQuantidadeDeProjetosPorCampus() {
		return ResponseEntity.ok(servico.obterQuantidadeTotalDeProjetosPorCampus());
	}
	
	@GetMapping("/usuario/total")
	public ResponseEntity<Integer> obterQuantidadeTotalDeUsuarios() {
		return ResponseEntity.ok(servico.obterQuantidadeTotalDeUsuarios());
	}
}
