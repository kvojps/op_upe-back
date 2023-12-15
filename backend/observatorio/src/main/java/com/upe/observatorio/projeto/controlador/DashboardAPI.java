package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.model.vos.DashboardResumoVO;
import com.upe.observatorio.projeto.model.vos.DashboardVO;
import com.upe.observatorio.projeto.service.DashboardService;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dashboard")
@CrossOrigin
@RequiredArgsConstructor
public class DashboardAPI {

	private final DashboardService servico;

	@GetMapping
	public ResponseEntity<DashboardVO> obterDashboard() throws ObservatorioExcecao {
		return ResponseEntity.ok(servico.getDashboard());
	}

	@GetMapping("/resumo")
	public ResponseEntity<DashboardResumoVO> obterDashboardResumo() {
		return ResponseEntity.ok(servico.getDashboardOverview());
	}
}
