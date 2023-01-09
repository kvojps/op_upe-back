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

import com.upe.observatorio.projeto.controller.model.CursoProjetoRepresentation;
import com.upe.observatorio.projeto.domain.CursoProjeto;
import com.upe.observatorio.projeto.domain.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.service.CursoProjetoService;
import com.upe.observatorio.projeto.utilities.ObservatorioException;

@RestController
@RequestMapping("api/curso-projeto")
public class CursoProjetoAPI {

	@Autowired
	private CursoProjetoService servico;

	@GetMapping
	public ResponseEntity<List<CursoProjetoRepresentation>> listarCursoProjetos() {
		return ResponseEntity.ok(servico.listarCursoProjetos().stream().map(cursoProjeto -> convert(cursoProjeto))
				.collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CursoProjetoRepresentation> buscarCursoProjetoPorId(@PathVariable("id") Long id) {
		CursoProjetoRepresentation resultado = convert(servico.buscarCursoProjetoPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}

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

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerCursoProjeto(@PathVariable("id") Long id) {
		try {
			servico.removerCursoProjeto(id);
		} catch (ObservatorioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	private CursoProjetoRepresentation convert(CursoProjeto entidade) {
		ModelMapper modelMapper = new ModelMapper();
		CursoProjetoRepresentation resultado = modelMapper.map(entidade, CursoProjetoRepresentation.class);

		return resultado;
	}

}
