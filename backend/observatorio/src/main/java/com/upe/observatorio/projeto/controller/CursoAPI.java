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

import com.upe.observatorio.projeto.controller.model.CursoRepresentation;
import com.upe.observatorio.projeto.domain.Curso;
import com.upe.observatorio.projeto.domain.dto.CursoDTO;
import com.upe.observatorio.projeto.service.CursoService;
import com.upe.observatorio.projeto.utilities.ObservatorioException;

@RestController
@RequestMapping("api/curso")
public class CursoAPI {

	@Autowired
	private CursoService servico;

	@GetMapping
	public ResponseEntity<List<CursoRepresentation>> listarCursos() {
		return ResponseEntity
				.ok(servico.listarCursos().stream().map(projeto -> convert(projeto)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CursoRepresentation> buscarCursoPorId(@PathVariable("id") Long id) {
		CursoRepresentation resultado = convert(servico.buscarCursoPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}

	@PostMapping
	public ResponseEntity<?> adicionarCurso(@RequestBody @Valid CursoDTO curso) {
		try {
			CursoRepresentation resultado = convert(servico.adicionarCurso(curso));

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ObservatorioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarCurso(@RequestBody @Valid CursoDTO curso, @PathVariable Long id) {
		try {
			servico.atualizarCurso(curso, id);
		} catch (ObservatorioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerCurso(@PathVariable("id") Long id) {
		try {
			servico.removerCurso(id);
		} catch (ObservatorioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	private CursoRepresentation convert(Curso entidade) {
		ModelMapper modelMapper = new ModelMapper();
		CursoRepresentation resultado = modelMapper.map(entidade, CursoRepresentation.class);

		return resultado;
	}

}
