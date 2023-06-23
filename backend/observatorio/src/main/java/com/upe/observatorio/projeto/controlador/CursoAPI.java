package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.controlador.modelo.CursoRepresentacao;
import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.dto.CursoDTO;
import com.upe.observatorio.projeto.servico.CursoServico;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/curso")
@CrossOrigin
@RequiredArgsConstructor
public class CursoAPI {

	private final CursoServico servico;

	@GetMapping
	public ResponseEntity<List<CursoRepresentacao>> listarCursos() {
		return ResponseEntity
				.ok(servico.listarCursos().stream().map(CursoRepresentacao::new).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarCursoPorId(@PathVariable("id") Long id) {
		ResponseEntity<?> resposta;
		try {
			Curso curso = servico.buscarCursoPorId(id).orElseThrow();
			CursoRepresentacao resultado = new CursoRepresentacao(curso);
			resposta = ResponseEntity.ok(resultado);
		} catch (ObservatorioExcecao e) {
			resposta = ResponseEntity.badRequest().body(e.getMessage());
		}

		return resposta;
	}

	@PostMapping
	public ResponseEntity<?> adicionarCurso(@RequestBody @Valid CursoDTO curso) {
		try {
			CursoRepresentacao resultado = new CursoRepresentacao(servico.adicionarCurso(curso));
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

}
