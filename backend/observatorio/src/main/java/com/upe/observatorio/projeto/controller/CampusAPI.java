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

import com.upe.observatorio.projeto.controller.model.CampusRepresentation;
import com.upe.observatorio.projeto.domain.dto.CampusDTO;
import com.upe.observatorio.projeto.service.CampusService;
import com.upe.observatorio.projeto.utilities.ProjetoException;

@RestController
@RequestMapping("api/campus")
public class CampusAPI {

	@Autowired
	private CampusService servico;
	
	// listar

	// buscar por id

	@PostMapping
	public ResponseEntity<?> adicionarCampus(@RequestBody @Valid CampusDTO campus) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			CampusRepresentation resultado = modelMapper.map(servico.adicionarCampus(campus),
					CampusRepresentation.class);

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ProjetoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// atualizar projeto

	// remover projeto
	
	//filtros
}
