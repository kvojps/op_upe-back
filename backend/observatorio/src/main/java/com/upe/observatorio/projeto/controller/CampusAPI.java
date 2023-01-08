package com.upe.observatorio.projeto.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.projeto.controller.model.CampusRepresentation;
import com.upe.observatorio.projeto.domain.Campus;
import com.upe.observatorio.projeto.domain.dto.CampusDTO;
import com.upe.observatorio.projeto.service.CampusService;
import com.upe.observatorio.projeto.utilities.ProjetoException;

@RestController
@RequestMapping("api/campus")
public class CampusAPI {

	@Autowired
	private CampusService servico;
	
	@GetMapping
	public ResponseEntity<List<CampusRepresentation>> listarCampus() {
		return ResponseEntity
				.ok(servico.listarCampus().stream().map(campus -> convert(campus)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CampusRepresentation> buscarCampusPorId(@PathVariable("id") Long id) {
		CampusRepresentation resultado = convert(servico.buscarCampusPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}

	@PostMapping
	public ResponseEntity<?> adicionarCampus(@RequestBody @Valid CampusDTO campus) {
		try {
			CampusRepresentation resultado = convert(servico.adicionarCampus(campus));

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ProjetoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarCampus(@RequestBody @Valid CampusDTO campus, @PathVariable Long id) {
		try {
			servico.atualizarCampus(campus, id);
		} catch (ProjetoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerCampus(@PathVariable("id") Long id) {
		try {
			servico.removerCampus(id);
		} catch (ProjetoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	private CampusRepresentation convert(Campus entidade) {
		ModelMapper modelMapper = new ModelMapper();
		CampusRepresentation resultado = modelMapper.map(entidade, CampusRepresentation.class);

		return resultado;
	}
}
