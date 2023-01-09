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

import com.upe.observatorio.projeto.controller.model.CursoProjetoRepresentation;
import com.upe.observatorio.projeto.domain.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.service.CursoProjetoService;
import com.upe.observatorio.projeto.utilities.ObservatorioException;

@RestController
@RequestMapping("api/curso-projeto")
public class CursoProjetoAPI {
	
	@Autowired
	private CursoProjetoService servico;
	
	// listar

	// buscar por id

	@PostMapping
	public ResponseEntity<?> adicionarCursoProjeto(@RequestBody @Valid CursoProjetoDTO cursoProjeto) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			CursoProjetoRepresentation resultado = modelMapper.map(servico.adicionarCursoProjeto(cursoProjeto),
					CursoProjetoRepresentation.class);

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ObservatorioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// atualizar projeto

	// remover projeto
	
	//filtros
}
