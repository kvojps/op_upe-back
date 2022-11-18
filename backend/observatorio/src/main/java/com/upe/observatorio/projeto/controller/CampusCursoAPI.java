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

import com.upe.observatorio.projeto.controller.model.CampusCursoRepresentation;
import com.upe.observatorio.projeto.domain.dto.CampusCursoDTO;
import com.upe.observatorio.projeto.service.CampusCursoService;
import com.upe.observatorio.projeto.utilities.ProjetoException;

@RestController
@RequestMapping("api/campus-curso")
public class CampusCursoAPI {
	
	@Autowired
	private CampusCursoService servico;
	
	// listar

	// buscar por id

	@PostMapping
	public ResponseEntity<?> adicionarCampusCurso(@RequestBody @Valid CampusCursoDTO campusCurso) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			CampusCursoRepresentation resultado = modelMapper.map(servico.adicionarCampusCurso(campusCurso),
					CampusCursoRepresentation.class);

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ProjetoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// atualizar projeto

	// remover projeto
	
	//filtros

}
