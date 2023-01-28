package com.upe.observatorio.usuario.controller;

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

import com.upe.observatorio.usuario.controller.model.UsuarioRepresentation;
import com.upe.observatorio.usuario.domain.Usuario;
import com.upe.observatorio.usuario.domain.dto.CriarPerfilUsuarioDTO;
import com.upe.observatorio.usuario.domain.dto.UsuarioDTO;
import com.upe.observatorio.usuario.service.UsuarioService;
import com.upe.observatorio.utils.ObservatorioException;

@RestController
@RequestMapping("api/usuario")
public class UsuarioAPI {

	@Autowired
	private UsuarioService servico;

	@GetMapping
	public ResponseEntity<List<UsuarioRepresentation>> listarUsuarios() {
		return ResponseEntity
				.ok(servico.listarUsuarios().stream().map(usuario -> convert(usuario)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioRepresentation> buscarUsuarioPorId(@PathVariable("id") Long id) {
		UsuarioRepresentation resultado = convert(servico.buscarUsuarioPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}

	@PostMapping
	public ResponseEntity<?> adicionarUsuario(@RequestBody @Valid UsuarioDTO usuario) {
		try {
			UsuarioRepresentation resultado = convert(servico.adicionarUsuario(usuario));

			return ResponseEntity.status(HttpStatus.OK).body(resultado);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarUsuario(@RequestBody @Valid UsuarioDTO usuario, @PathVariable Long id) {
		try {
			servico.atualizarUsuario(usuario, id);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerUsuario(@PathVariable("id") Long id) {
		try {
			servico.removerUsuario(id);
		} catch (ObservatorioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PutMapping
	public ResponseEntity<?> adicionarPerfisUsuario(@RequestBody @Valid CriarPerfilUsuarioDTO addPerfil) {
		try {
			UsuarioRepresentation resultado = convert(servico.adicionarPerfisUsuario(addPerfil));

			return ResponseEntity.status(HttpStatus.OK).body(resultado);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	private UsuarioRepresentation convert(Usuario entidade) {
		ModelMapper modelMapper = new ModelMapper();
		UsuarioRepresentation resultado = modelMapper.map(entidade, UsuarioRepresentation.class);

		return resultado;
	}
}
