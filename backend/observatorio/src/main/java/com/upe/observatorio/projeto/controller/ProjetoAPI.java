package com.upe.observatorio.projeto.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.projeto.controller.model.ProjetoRepresentation;
import com.upe.observatorio.projeto.domain.Projeto;
import com.upe.observatorio.projeto.domain.dto.ProjetoDTO;
import com.upe.observatorio.projeto.service.ProjetoService;
import com.upe.observatorio.projeto.utilities.ProjetoException;

@RestController
@RequestMapping("api/projeto")
public class ProjetoAPI {

	@Autowired
	private ProjetoService service;

	@GetMapping
	public ResponseEntity<List<ProjetoRepresentation>> listarProjetos() {
		return ResponseEntity
				.ok(service.listarProjetos().stream().map(projeto -> convert(projeto)).collect(Collectors.toList()));
	}

	// buscar por id

	@PostMapping
	public ResponseEntity<?> adicionarProjeto(@RequestBody @Valid ProjetoDTO projeto) {
		try {
			ProjetoRepresentation resultado = convert(service.adicionarProjeto(projeto));

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ProjetoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// atualizar projeto

	// remover projeto

	// filtrar por area tematica

	// filtrar por modalidade

	// filtrar por titulo

	private ProjetoRepresentation convert(Projeto entidade) {
		ModelMapper modelMapper = new ModelMapper();
		ProjetoRepresentation resultado = modelMapper.map(entidade, ProjetoRepresentation.class);
		
		return resultado;
	}
}
