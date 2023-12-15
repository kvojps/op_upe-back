package com.upe.observatorio.usuario.controlador;

import com.upe.observatorio.usuario.controlador.model.UsuarioRepresentacao;
import com.upe.observatorio.usuario.dominio.Usuario;
import com.upe.observatorio.usuario.dominio.dto.AutenticacaoResponseDTO;
import com.upe.observatorio.usuario.dominio.dto.CadastroRequestDTO;
import com.upe.observatorio.usuario.dominio.dto.UsuarioDTO;
import com.upe.observatorio.usuario.servico.UsuarioServico;
import com.upe.observatorio.utils.ObservatoryException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/usuarios")
@CrossOrigin
@RequiredArgsConstructor
public class UsuarioAPI {

    private final UsuarioServico servico;

    @GetMapping
    public ResponseEntity<List<UsuarioRepresentacao>> listarUsuarios() {
        return ResponseEntity
                .ok(servico.listarUsuarios().stream().map(UsuarioRepresentacao::new).collect(Collectors.toList()));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioRepresentacao> buscarUsuarioPorPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) authentication.getPrincipal();

        return ResponseEntity.ok(new UsuarioRepresentacao(servico.buscarUsuarioPorId(user.getId())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRepresentacao> buscarUsuarioPorId(@PathVariable("id") Long id) {
		return ResponseEntity.ok(new UsuarioRepresentacao(servico.buscarUsuarioPorId(id)));
    }

    @PostMapping
    public ResponseEntity<AutenticacaoResponseDTO> cadastrarUsuario(@Valid @RequestBody CadastroRequestDTO request,
                                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ObservatoryException(String.join("; ", bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
        }

        return ResponseEntity.ok(servico.cadastrarUsuario(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarUsuario(@RequestBody @Valid UsuarioDTO usuario, @PathVariable Long id) {
        servico.atualizarUsuario(usuario, id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable("id") Long id) {
        servico.removerUsuario(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
