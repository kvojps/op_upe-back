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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.publicacao.controller.model.PublicacaoRepresentation;
import com.upe.observatorio.publicacao.domain.Publicacao;
import com.upe.observatorio.publicacao.domain.dto.PublicacaoDTO;
import com.upe.observatorio.publicacao.service.PublicacaoService;
import com.upe.observatorio.utils.ObservatorioException;

@RestController
@RequestMapping("api/publicacao")
public class PublicacaoAPI {
	@Autowired
	private PublicacaoService service;

	@GetMapping
	public ResponseEntity<List<PublicacaoRepresentation>> listarPublicacoes() {
		return ResponseEntity
				.ok(service.listarPublicacoes().stream().map(projeto -> convert(projeto)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PublicacaoRepresentation> buscarPublicacaoPorId(@PathVariable("id") Long id) {
		PublicacaoRepresentation resultado = convert(service.buscarPublicacaoPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}
	
	@PostMapping
	public ResponseEntity<?> adicionarPublicacao(@RequestBody @Valid PublicacaoDTO publicacao) {
		try {
			PublicacaoRepresentation resultado = convert(service.adicionarPublicacao(publicacao));

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ObservatorioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerPublicacao(@PathVariable("id") Long id) {
		try {
			service.removerPublicacao(id);
		} catch (ObservatorioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	private PublicacaoRepresentation convert(Publicacao entidade) {
		ModelMapper modelMapper = new ModelMapper();
		PublicacaoRepresentation resultado = modelMapper.map(entidade, PublicacaoRepresentation.class);

		return resultado;
	}
}
