package com.upe.observatorio.projeto.controlador;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.projeto.controlador.modelo.CampusRepresentacao;
import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.dto.CampusDTO;
import com.upe.observatorio.projeto.servico.CampusServico;
import com.upe.observatorio.utils.ObservatorioExcecao;

@RestController
@RequestMapping("api/campus")
@CrossOrigin
public class CampusAPI {

	@Autowired
	private CampusServico servico;

	@GetMapping
	public ResponseEntity<List<CampusRepresentacao>> listarCampus() {
		return ResponseEntity
				.ok(servico.listarCampus().stream().map(campus -> convert(campus)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CampusRepresentacao> buscarCampusPorId(@PathVariable("id") Long id)
			throws ObservatorioExcecao {
		CampusRepresentacao resultado = convert(servico.buscarCampusPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}

	@PostMapping
	public ResponseEntity<?> adicionarCampus(@RequestBody @Valid CampusDTO campus) {
		try {
			CampusRepresentacao resultado = convert(servico.adicionarCampus(campus));

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
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

	private CampusRepresentacao convert(Campus entidade) {
		ModelMapper modelMapper = new ModelMapper();
		CampusRepresentacao resultado = modelMapper.map(entidade, CampusRepresentacao.class);

		return resultado;
	}
}
