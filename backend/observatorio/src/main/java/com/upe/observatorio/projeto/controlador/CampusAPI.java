package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.controlador.modelo.CampusRepresentacao;
import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.dto.CampusDTO;
import com.upe.observatorio.projeto.servico.CampusServico;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/campus")
@CrossOrigin
@RequiredArgsConstructor
public class CampusAPI {

	private final CampusServico servico;

	@GetMapping
	public ResponseEntity<List<CampusRepresentacao>> listarCampus() {
		return ResponseEntity
				.ok(servico.listarCampus().stream().map(CampusRepresentacao::new).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarCampusPorId(@PathVariable("id") Long id) {
		ResponseEntity<?> resposta;
		try {
			Campus campus = servico.buscarCampusPorId(id);
			CampusRepresentacao resultado = new CampusRepresentacao(campus);
			resposta = ResponseEntity.ok(resultado);
		} catch (ObservatorioExcecao e) {
			resposta = ResponseEntity.badRequest().body(e.getMessage());
		}

		return resposta;
	}

	@PostMapping
	public ResponseEntity<?> adicionarCampus(@RequestBody @Valid CampusDTO campus) {
		CampusRepresentacao resultado = new CampusRepresentacao(servico.adicionarCampus(campus));
		return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarCampus(@RequestBody @Valid CampusDTO campus, @PathVariable Long id) {
		try {
			servico.atualizarCampus(campus, id);
		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerCampus(@PathVariable("id") Long id) {
		try {
			servico.removerCampus(id);
		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
