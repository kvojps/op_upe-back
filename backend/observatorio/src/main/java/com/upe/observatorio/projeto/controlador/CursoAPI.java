package com.upe.observatorio.projeto.controlador;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.projeto.controlador.modelo.CursoRepresentacao;
import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.dto.CursoDTO;
import com.upe.observatorio.projeto.servico.CursoServico;
import com.upe.observatorio.utils.ObservatorioExcecao;

@RestController
@RequestMapping("api/curso")
@CrossOrigin
public class CursoAPI {

	@Autowired
	private CursoServico servico;

	@GetMapping
	public ResponseEntity<List<CursoRepresentacao>> listarCursos() {
		return ResponseEntity
				.ok(servico.listarCursos().stream().map(projeto -> convert(projeto)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CursoRepresentacao> buscarCursoPorId(@PathVariable("id") Long id) throws ObservatorioExcecao {
		CursoRepresentacao resultado = convert(servico.buscarCursoPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}

	@PostMapping
	public ResponseEntity<?> adicionarCurso(@RequestBody @Valid CursoDTO curso) {
		try {
			CursoRepresentacao resultado = convert(servico.adicionarCurso(curso));

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarCurso(@RequestBody @Valid CursoDTO curso, @PathVariable Long id) {
		try {
			servico.atualizarCurso(curso, id);
		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerCurso(@PathVariable("id") Long id) {
		try {
			servico.removerCurso(id);
		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	private CursoRepresentacao convert(Curso entidade) {
		ModelMapper modelMapper = new ModelMapper();
		CursoRepresentacao resultado = modelMapper.map(entidade, CursoRepresentacao.class);

		return resultado;
	}

}
