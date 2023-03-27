package com.upe.observatorio.publicacao.controlador;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.publicacao.controlador.modelo.ComentarioRepresentacao;
import com.upe.observatorio.publicacao.dominio.Comentario;
import com.upe.observatorio.publicacao.dominio.dto.AtualizarComentarioDTO;
import com.upe.observatorio.publicacao.dominio.dto.ComentarioDTO;
import com.upe.observatorio.publicacao.servico.ComentarioServico;
import com.upe.observatorio.utils.ObservatorioExcecao;

@RestController
@RequestMapping("api/comentario")
public class ComentarioAPI {
	
	@Autowired
	private ComentarioServico servico;
	
	@GetMapping
	public ResponseEntity<List<ComentarioRepresentacao>> listarComentarios() {
		return ResponseEntity
				.ok(servico.listarComentarios().stream().map(projeto -> convert(projeto)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ComentarioRepresentacao> buscarComentarioPorId(@PathVariable("id") Long id) {
		ComentarioRepresentacao resultado = convert(servico.buscarComentarioPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}
	
	@PostMapping
	public ResponseEntity<?> adicionarComentario(@RequestBody @Valid ComentarioDTO comentario) {
		try {
			ComentarioRepresentacao resultado = convert(servico.adicionarComentario(comentario));

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
	
	private ComentarioRepresentacao convert(Comentario entidade) {
		ModelMapper modelMapper = new ModelMapper();
		ComentarioRepresentacao resultado = modelMapper.map(entidade, ComentarioRepresentacao.class);

		return resultado;
	}
}
