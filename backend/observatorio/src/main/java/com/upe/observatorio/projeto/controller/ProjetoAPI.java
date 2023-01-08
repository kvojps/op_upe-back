package com.upe.observatorio.projeto.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("/{id}")
	public ResponseEntity<ProjetoRepresentation> buscarProjetoPorId(@PathVariable("id") Long id) {
		ProjetoRepresentation resultado = convert(service.buscarProjetoPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}

	@PostMapping
	public ResponseEntity<?> adicionarProjeto(@RequestBody @Valid ProjetoDTO projeto) {
		try {
			ProjetoRepresentation resultado = convert(service.adicionarProjeto(projeto));

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ProjetoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarProjeto(@RequestBody @Valid ProjetoDTO projeto, @PathVariable Long id) {
		try {
			service.atualizarProjeto(projeto, id);
		} catch (ProjetoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerProjeto(@PathVariable("id") Long id) {
		try {
			service.removerProjeto(id);
		} catch (ProjetoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("filtro/area-tematica")
	public ResponseEntity<Page<ProjetoRepresentation>> filtrarProjetoPorAreaTematica(
			@RequestParam(value = "areaTematica", required = true) String areaTematica,
			@PageableDefault(size = 10) Pageable pageable) {

		List<ProjetoRepresentation> projetosFiltrados = service.filtrarProjetoPorAreaTematica(areaTematica).stream()
				.map(projeto -> convert(projeto)).collect(Collectors.toList());
		Page<ProjetoRepresentation> paginas = new PageImpl<ProjetoRepresentation>(projetosFiltrados, pageable,
				projetosFiltrados.size());
		
		return ResponseEntity.ok(paginas);
	}

	@GetMapping("filtro/modalidade")
	public ResponseEntity<Page<ProjetoRepresentation>> filtrarProjetoPorModalidade(
			@RequestParam(value = "modalidade", required = true) String modalidade,
			@PageableDefault(size = 10) Pageable pageable) {
		List<ProjetoRepresentation> projetosFiltrados = service.filtrarProjetoPorModalidade(modalidade).stream()
				.map(projeto -> convert(projeto)).collect(Collectors.toList());
		Page<ProjetoRepresentation> paginas = new PageImpl<ProjetoRepresentation>(projetosFiltrados, pageable,
				projetosFiltrados.size());
		
		return ResponseEntity.ok(paginas);
	}

	@GetMapping("filtro/titulo")
	public ResponseEntity<Page<ProjetoRepresentation>> filtrarProjetoPorTitulo(
			@RequestParam(value = "titulo", required = true) String titulo,
			@PageableDefault(size = 10) Pageable pageable) {
		List<ProjetoRepresentation> projetosFiltrados = service.filtrarProjetoPorTitulo(titulo).stream()
				.map(projeto -> convert(projeto)).collect(Collectors.toList());
		Page<ProjetoRepresentation> paginas = new PageImpl<ProjetoRepresentation>(projetosFiltrados, pageable,
				projetosFiltrados.size());
		
		return ResponseEntity.ok(paginas);
	}

	private ProjetoRepresentation convert(Projeto entidade) {
		ModelMapper modelMapper = new ModelMapper();
		ProjetoRepresentation resultado = modelMapper.map(entidade, ProjetoRepresentation.class);

		return resultado;
	}
}
