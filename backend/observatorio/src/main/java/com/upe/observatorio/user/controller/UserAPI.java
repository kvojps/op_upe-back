package com.upe.observatorio.user.controller;

import com.upe.observatorio.user.controller.response.UsuarioRepresentacao;
import com.upe.observatorio.user.dominio.Usuario;
import com.upe.observatorio.user.dominio.dto.AutenticacaoResponseDTO;
import com.upe.observatorio.user.dominio.dto.CadastroRequestDTO;
import com.upe.observatorio.user.dominio.dto.UsuarioDTO;
import com.upe.observatorio.user.servico.UsuarioServico;
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
public class UserAPI {

    private final UsuarioServico service;

    @GetMapping
    public ResponseEntity<List<UsuarioRepresentacao>> readUsers() {
        return ResponseEntity
                .ok(service.listarUsuarios().stream().map(UsuarioRepresentacao::new).collect(Collectors.toList()));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioRepresentacao> findUserByPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) authentication.getPrincipal();

        return ResponseEntity.ok(new UsuarioRepresentacao(service.buscarUsuarioPorId(user.getId())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRepresentacao> findUserById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(new UsuarioRepresentacao(service.buscarUsuarioPorId(id)));
    }

    @PostMapping
    public ResponseEntity<AutenticacaoResponseDTO> createUser(@Valid @RequestBody CadastroRequestDTO request,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ObservatoryException(String.join("; ", bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
        }

        return ResponseEntity.ok(service.cadastrarUsuario(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UsuarioDTO user, @PathVariable Long id) {
        service.atualizarUsuario(user, id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        service.removerUsuario(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
