package com.upe.observatorio.project.controller;

import com.upe.observatorio.project.model.vos.DashboardOverviewVO;
import com.upe.observatorio.project.model.vos.DashboardVO;
import com.upe.observatorio.project.service.DashboardService;
import com.upe.observatorio.utils.ObservatoryException;
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

	private final DashboardService service;

	@GetMapping
	public ResponseEntity<DashboardVO> getDashboard() throws ObservatoryException {
		return ResponseEntity.ok(service.getDashboard());
	}

	@GetMapping("/resumo")
	public ResponseEntity<DashboardOverviewVO> getDashboardOverview() {
		return ResponseEntity.ok(service.getDashboardOverview());
	}
}
