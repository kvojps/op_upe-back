package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.dominio.envelopes.DashboardResumoVO;
import com.upe.observatorio.projeto.dominio.envelopes.DashboardVO;
import com.upe.observatorio.projeto.servico.DashboardServico;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("api/dashboard")
@CrossOrigin
@RequiredArgsConstructor
public class DashboardAPI {

	private final DashboardServico servico;

	@GetMapping
	public ResponseEntity<DashboardVO> obterDashboard() throws ObservatorioExcecao {
		return ResponseEntity.ok(servico.gerarDashboard());
	}

	@GetMapping("/resumo")
	public ResponseEntity<DashboardResumoVO> obterDashboardResumo() {
		return ResponseEntity.ok(servico.gerarDashboardResumo());
	}

//	@GetMapping("/projeto/total")
//	public ResponseEntity<Integer> obterQuantidadeTotalDeProjetos() {
//		return ResponseEntity.ok(servico.obterQuantidadeTotalDeProjetos());
//	}
//
//	@GetMapping("/projeto/areaTematica")
//	public ResponseEntity<HashMap<String, Integer>> obterQuantidadeDeProjetosPorAreaTematica() {
//		return ResponseEntity.ok(servico.obterQuantidadeTotalDeProjetosPorAreaTematica());
//	}
//
//	@GetMapping("/projeto/modalidade")
//	public ResponseEntity<HashMap<String, Integer>> obterQuantidadeDeProjetosPorModalidade() {
//		return ResponseEntity.ok(servico.obterQuantidadeTotalDeProjetosPorModalidade());
//	}
//
//	@GetMapping("projeto/curso")
//	public ResponseEntity<HashMap<String, Integer>> obterQuantidadeDeProjetosPorCurso() throws ObservatorioExcecao {
//		return ResponseEntity.ok(servico.obterQuantidadeTotalDeProjetosPorCurso());
//	}
//
//	@GetMapping("projeto/campus")
//	public ResponseEntity<HashMap<String, Integer>> obterQuantidadeDeProjetosPorCampus() {
//		return ResponseEntity.ok(servico.obterQuantidadeTotalDeProjetosPorCampus());
//	}
//
//	@GetMapping("/usuario/total")
//	public ResponseEntity<Integer> obterQuantidadeTotalDeUsuarios() {
//		return ResponseEntity.ok(servico.obterQuantidadeTotalDeUsuarios());
//	}
}
