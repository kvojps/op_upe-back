package com.upe.observatorio.usuario.controlador;

import com.upe.observatorio.usuario.dominio.dto.AutenticacaoRequestDTO;
import com.upe.observatorio.usuario.dominio.dto.AutenticacaoResponseDTO;
import com.upe.observatorio.usuario.servico.UsuarioServico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthAPI {

    private final UsuarioServico servico;

    @PostMapping
    public ResponseEntity<AutenticacaoResponseDTO> loginUsuario(@RequestBody AutenticacaoRequestDTO request) {
        return ResponseEntity.ok(servico.loginUsuario(request));
    }
}
