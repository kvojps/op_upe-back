package com.upe.observatorio.projeto.controlador;

import java.util.Date;
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

import com.upe.observatorio.projeto.controlador.modelo.ProjetoRepresentacao;
import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.dto.ProjetoDTO;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import com.upe.observatorio.projeto.servico.ProjetoServico;
import com.upe.observatorio.utils.ObservatorioExcecao;

@RestController
@RequestMapping("api/projeto")
public class ProjetoAPI {

	@Autowired
	private ProjetoServico servico;

	@GetMapping
	public ResponseEntity<List<ProjetoRepresentacao>> listarProjetos() {
		return ResponseEntity
				.ok(servico.listarProjetos().stream().map(projeto -> convert(projeto)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProjetoRepresentacao> buscarProjetoPorId(@PathVariable("id") Long id) {
		ProjetoRepresentacao resultado = convert(servico.buscarProjetoPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}

	@PostMapping
	public ResponseEntity<?> adicionarProjeto(@RequestBody @Valid ProjetoDTO projeto) {
		try {
			ProjetoRepresentacao resultado = convert(servico.adicionarProjeto(projeto));

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarProjeto(@RequestBody @Valid ProjetoDTO projeto, @PathVariable Long id) {
		try {
			servico.atualizarProjeto(projeto, id);
		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerProjeto(@PathVariable("id") Long id) {
		try {
			servico.removerProjeto(id);
		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("filtro/titulo")
	public ResponseEntity<Page<ProjetoRepresentacao>> filtrarProjetoPorTitulo(
			@RequestParam(value = "titulo", required = true) String titulo,
			@PageableDefault(size = 10) Pageable pageable) {
		List<ProjetoRepresentacao> projetosFiltrados = servico.filtrarProjetoPorTitulo(titulo).stream()
				.map(projeto -> convert(projeto)).collect(Collectors.toList());
		Page<ProjetoRepresentacao> paginas = new PageImpl<ProjetoRepresentacao>(projetosFiltrados, pageable,
				projetosFiltrados.size());

		return ResponseEntity.ok(paginas);
	}

	@GetMapping("/filtro")
	public ResponseEntity<Page<ProjetoRepresentacao>> filtrarProjetoComTodosFiltros(
			@RequestParam(value = "areaTematica", required = true) AreaTematicaEnum areaTematica,
			@RequestParam(value = "modalidade", required = true) ModalidadeEnum modalidade,
			@RequestParam(value = "dataInicio", required = true) Date dataInicio,
			@RequestParam(value = "dataFim", required = true) Date dataFim,
			@PageableDefault(size = 10) Pageable pageable) {

		List<ProjetoRepresentacao> projetosFiltrados = servico
				.filtrarProjetoComTodosFiltros(areaTematica, modalidade, dataInicio, dataFim).stream()
				.map(projeto -> convert(projeto)).collect(Collectors.toList());
		Page<ProjetoRepresentacao> paginas = new PageImpl<ProjetoRepresentacao>(projetosFiltrados, pageable,
				projetosFiltrados.size());
		
		return ResponseEntity.ok(paginas);
	}

	private ProjetoRepresentacao convert(Projeto entidade) {
		ModelMapper modelMapper = new ModelMapper();
		ProjetoRepresentacao resultado = modelMapper.map(entidade, ProjetoRepresentacao.class);

		return resultado;
	}
}
