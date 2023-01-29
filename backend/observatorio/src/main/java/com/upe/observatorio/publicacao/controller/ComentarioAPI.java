package com.upe.observatorio.publicacao.controller;

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

import com.upe.observatorio.publicacao.controller.model.ComentarioRepresentation;
import com.upe.observatorio.publicacao.domain.Comentario;
import com.upe.observatorio.publicacao.domain.dto.AtualizarComentarioDTO;
import com.upe.observatorio.publicacao.domain.dto.ComentarioDTO;
import com.upe.observatorio.publicacao.service.ComentarioService;
import com.upe.observatorio.utils.ObservatorioException;

@RestController
@RequestMapping("api/comentario")
public class ComentarioAPI {
	
	@Autowired
	private ComentarioService service;
	
	@GetMapping
	public ResponseEntity<List<ComentarioRepresentation>> listarComentarios() {
		return ResponseEntity
				.ok(service.listarComentarios().stream().map(projeto -> convert(projeto)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ComentarioRepresentation> buscarComentarioPorId(@PathVariable("id") Long id) {
		ComentarioRepresentation resultado = convert(service.buscarComentarioPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}
	
	@PostMapping
	public ResponseEntity<?> adicionarComentario(@RequestBody @Valid ComentarioDTO comentario) {
		try {
			ComentarioRepresentation resultado = convert(service.adicionarComentario(comentario));

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ObservatorioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarComentario(@RequestBody @Valid AtualizarComentarioDTO comentario, @PathVariable Long id) {
		try {
			service.atualizarComentario(comentario, id);
		} catch (ObservatorioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerComentario(@PathVariable("id") Long id) {
		try {
			service.removerComentario(id);
		} catch (ObservatorioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	private ComentarioRepresentation convert(Comentario entidade) {
		ModelMapper modelMapper = new ModelMapper();
		ComentarioRepresentation resultado = modelMapper.map(entidade, ComentarioRepresentation.class);

		return resultado;
	}
}
