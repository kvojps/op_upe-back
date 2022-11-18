package com.upe.observatorio.projeto.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.projeto.controller.model.CursoRepresentation;
import com.upe.observatorio.projeto.domain.dto.CursoDTO;
import com.upe.observatorio.projeto.service.CursoService;
import com.upe.observatorio.projeto.utilities.ProjetoException;

@RestController
@RequestMapping("api/curso")
public class CursoAPI {
	
	@Autowired
	private CursoService servico;
	
	// listar

	// buscar por id

	@PostMapping
	public ResponseEntity<?> adicionarCampusCurso(@RequestBody @Valid CursoDTO curso) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			CursoRepresentation resultado = modelMapper.map(servico.adicionarCurso(curso),
					CursoRepresentation.class);

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ProjetoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// atualizar projeto

	// remover projeto
	
	//filtros
}
