package com.upe.observatorio.usuario.controlador;

import com.upe.observatorio.usuario.controlador.model.UsuarioRepresentacao;
import com.upe.observatorio.usuario.dominio.Usuario;
import com.upe.observatorio.usuario.dominio.dto.AutenticacaoRequestDTO;
import com.upe.observatorio.usuario.dominio.dto.AutenticacaoResponseDTO;
import com.upe.observatorio.usuario.dominio.dto.CadastroRequestDTO;
import com.upe.observatorio.usuario.dominio.dto.UsuarioDTO;
import com.upe.observatorio.usuario.servico.UsuarioServico;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/usuario")
@CrossOrigin
@RequiredArgsConstructor
public class UsuarioAPI {

    private final UsuarioServico servico;

    @GetMapping
    public ResponseEntity<List<UsuarioRepresentacao>> listarUsuarios() {
        return ResponseEntity
                .ok(servico.listarUsuarios().stream().map(UsuarioRepresentacao::new).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable("id") Long id) {
		ResponseEntity<?> resposta;
		try {
			Usuario usuario = servico.buscarUsuarioPorId(id).orElseThrow();
			UsuarioRepresentacao resultado = new UsuarioRepresentacao(usuario);
			resposta = ResponseEntity.ok(resultado);
		} catch (ObservatorioExcecao e) {
			resposta = ResponseEntity.badRequest().body(e.getMessage());
		}

		return resposta;
    }

    @PostMapping("/auth/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody CadastroRequestDTO request) {
        ResponseEntity<?> resposta;
        try {
            resposta = ResponseEntity.ok(servico.cadastrarUsuario(request));
        } catch (ObservatorioExcecao e) {
            resposta = ResponseEntity.badRequest().body(e.getMessage());
        }

        return resposta;
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
}
