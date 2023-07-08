package com.upe.observatorio.publicacao.controlador;

import com.upe.observatorio.publicacao.controlador.modelo.ComentarioRepresentacao;
import com.upe.observatorio.publicacao.dominio.Comentario;
import com.upe.observatorio.publicacao.dominio.dto.AtualizarComentarioDTO;
import com.upe.observatorio.publicacao.dominio.dto.ComentarioDTO;
import com.upe.observatorio.publicacao.servico.ComentarioServico;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/comentario")
@CrossOrigin
@RequiredArgsConstructor
public class ComentarioAPI {

	private final ComentarioServico servico;

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarComentarioPorId(@PathVariable("id") Long id) {
		ResponseEntity<?> resposta;
		try {
			Comentario comentario = servico.buscarComentarioPorId(id).orElseThrow();
			ComentarioRepresentacao resultado = new ComentarioRepresentacao(comentario);
			resposta = ResponseEntity.ok(resultado);
		} catch (ObservatorioExcecao e) {
			resposta = ResponseEntity.badRequest().body(e.getMessage());
		}

		return resposta;
	}
	
	@PostMapping
	public ResponseEntity<?> adicionarComentario(@RequestBody @Valid ComentarioDTO comentario) {
		try {
			ComentarioRepresentacao resultado = new ComentarioRepresentacao(servico.adicionarComentario(comentario));
			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarComentario(@RequestBody @Valid AtualizarComentarioDTO comentario, @PathVariable Long id) {
		try {
			servico.atualizarComentario(comentario, id);
		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerComentario(@PathVariable("id") Long id) {
		try {
			servico.removerComentario(id);
		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
