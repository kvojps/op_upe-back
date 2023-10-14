package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.controlador.modelo.CampusRepresentacao;
import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.dto.CampusDTO;
import com.upe.observatorio.projeto.servico.CampusServico;
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

	@PostMapping
	public ResponseEntity<CampusRepresentacao> adicionarCampus(@RequestBody @Valid CampusDTO campus) {
		return ResponseEntity.status(HttpStatus.CREATED).body(new CampusRepresentacao(servico.adicionarCampus(campus)));
	}

	@GetMapping
	public ResponseEntity<List<CampusRepresentacao>> listarCampus() {
		return ResponseEntity
				.ok(servico.listarCampus().stream().map(CampusRepresentacao::new).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CampusRepresentacao> buscarCampusPorId(@PathVariable("id") Long id) {
		Campus campus = servico.buscarCampusPorId(id);

		return ResponseEntity.ok(new CampusRepresentacao(campus));
	}
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizarCampus(@RequestBody @Valid CampusDTO campus, @PathVariable Long id) {
		servico.atualizarCampus(campus, id);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removerCampus(@PathVariable("id") Long id) {
		servico.removerCampus(id);

		return ResponseEntity.noContent().build();
	}
}
