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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.projeto.controller.model.CampusCursoRepresentation;
import com.upe.observatorio.projeto.domain.CampusCurso;
import com.upe.observatorio.projeto.domain.dto.CampusCursoDTO;
import com.upe.observatorio.projeto.service.CampusCursoService;
import com.upe.observatorio.utils.ObservatorioException;

@RestController
@RequestMapping("api/campus-curso")
public class CampusCursoAPI {

	@Autowired
	private CampusCursoService servico;

	@GetMapping
	public ResponseEntity<List<CampusCursoRepresentation>> listarCampusCurso() {
		return ResponseEntity
				.ok(servico.listarCampusCurso().stream().map(campus -> convert(campus)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CampusCursoRepresentation> buscarCampusCursoPorId(@PathVariable("id") Long id) {
		CampusCursoRepresentation resultado = convert(servico.buscarCampusCursoPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}

	@PostMapping
	public ResponseEntity<?> adicionarCampusCurso(@RequestBody @Valid CampusCursoDTO campusCurso) {
		try {
			CampusCursoRepresentation resultado = convert(servico.adicionarCampusCurso(campusCurso));

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ObservatorioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerCampusCurso(@PathVariable("id") Long id) {
		try {
			servico.removerCampusCurso(id);
		} catch (ObservatorioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	private CampusCursoRepresentation convert(CampusCurso entidade) {
		ModelMapper modelMapper = new ModelMapper();
		CampusCursoRepresentation resultado = modelMapper.map(entidade, CampusCursoRepresentation.class);

		return resultado;
	}

}
