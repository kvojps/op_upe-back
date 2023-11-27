package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.controlador.representacao.CampusRepresentacao;
import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.dto.CampusDTO;
import com.upe.observatorio.projeto.servico.CampusServico;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/campus")
@CrossOrigin
@RequiredArgsConstructor
public class CampusAPI {

	private final CampusServico servico;

	@PostMapping
	public ResponseEntity<CampusRepresentacao> adicionarCampus(
			@RequestBody @Valid CampusDTO campus,
			BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			throw new ObservatorioExcecao(String.join("; ", bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
		}

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
	public ResponseEntity<Void> atualizarCampus(
			@RequestBody @Valid CampusDTO campus,
			@PathVariable Long id,
			BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			throw new ObservatorioExcecao(String.join("; ", bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
		}

		servico.atualizarCampus(campus, id);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removerCampus(@PathVariable("id") Long id) {
		servico.removerCampus(id);

		return ResponseEntity.noContent().build();
	}
}
