package com.upe.observatorio.usuario.controlador;

import com.upe.observatorio.usuario.dominio.dto.AutenticacaoRequestDTO;
import com.upe.observatorio.usuario.dominio.dto.AutenticacaoResponseDTO;
import com.upe.observatorio.usuario.dominio.dto.ResetPasswordDTO;
import com.upe.observatorio.usuario.servico.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthAPI {

    private final AuthService servico;

    @PostMapping
    public ResponseEntity<AutenticacaoResponseDTO> loginUsuario(@RequestBody AutenticacaoRequestDTO request) {
        return ResponseEntity.ok(servico.loginUsuario(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestParam String email) {
        servico.forgotPassword(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        servico.resetPassword(resetPasswordDTO);
        return ResponseEntity.noContent().build();
    }
}
