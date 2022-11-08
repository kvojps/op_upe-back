package com.upe.observatorio.projeto.controller;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.projeto.controller.model.ProjetoRepresentation;
import com.upe.observatorio.projeto.domain.dto.ProjetoDTO;
import com.upe.observatorio.projeto.service.ProjetoService;
import com.upe.observatorio.projeto.utilities.ProjetoException;

@RestController
@RequestMapping("api/projeto")
public class ProjetoAPI {
	
	@Autowired
	private ProjetoService service;
	
	//listar
	
	//buscar por id
	
	@PostMapping
	public ResponseEntity<?> adicionarProjeto(@RequestBody @Valid ProjetoDTO projeto) {
		try {			
			ProjetoRepresentation projetoResultado = new ProjetoRepresentation();
			BeanUtils.copyProperties(service.adicionarProjeto(projeto), projetoResultado);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(projetoResultado);
			
		} catch (ProjetoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	//atualizar projeto
	
	//remover projeto
	
	//filtrar por area tematica
	
	//filtrar por modalidade
	
	//filtrar por titulo
}
