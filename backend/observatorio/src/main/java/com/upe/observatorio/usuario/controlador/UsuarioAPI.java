package com.upe.observatorio.usuario.controlador;

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

import com.upe.observatorio.usuario.controlador.model.UsuarioRepresentacao;
import com.upe.observatorio.usuario.dominio.Usuario;
import com.upe.observatorio.usuario.dominio.dto.AutenticacaoRequestDTO;
import com.upe.observatorio.usuario.dominio.dto.AutenticacaoResponseDTO;
import com.upe.observatorio.usuario.dominio.dto.CadastroRequestDTO;
import com.upe.observatorio.usuario.dominio.dto.UsuarioDTO;
import com.upe.observatorio.usuario.servico.UsuarioServico;
import com.upe.observatorio.utils.ObservatorioExcecao;

@RestController
@RequestMapping("api/usuario")
@CrossOrigin
public class UsuarioAPI {

	@Autowired
	private UsuarioServico servico;

	@GetMapping
	public ResponseEntity<List<UsuarioRepresentacao>> listarUsuarios() {
		return ResponseEntity
				.ok(servico.listarUsuarios().stream().map(usuario -> convert(usuario)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioRepresentacao> buscarUsuarioPorId(@PathVariable("id") Long id) {
		UsuarioRepresentacao resultado = convert(servico.buscarUsuarioPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}

	@PostMapping("/auth/cadastrar")
	public ResponseEntity<AutenticacaoResponseDTO> cadastrarUsuario(@RequestBody CadastroRequestDTO request) {
		return ResponseEntity.ok(servico.cadastrarUsuario(request));
	}

	@PostMapping("/auth/login")
	public ResponseEntity<AutenticacaoResponseDTO> loginUsuario(@RequestBody AutenticacaoRequestDTO request) {
		return ResponseEntity.ok(servico.loginUsuario(request));
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
		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	private UsuarioRepresentacao convert(Usuario entidade) {
		ModelMapper modelMapper = new ModelMapper();
		UsuarioRepresentacao resultado = modelMapper.map(entidade, UsuarioRepresentacao.class);

		return resultado;
	}
}
