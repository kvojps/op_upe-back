package com.upe.observatorio.user.controller;

import com.upe.observatorio.user.model.dto.AuthRequestDTO;
import com.upe.observatorio.user.model.dto.AuthResponseDTO;
import com.upe.observatorio.user.model.dto.ResetPasswordDTO;
import com.upe.observatorio.user.servico.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthAPI {

    private final AuthService service;

    @PostMapping
    public ResponseEntity<AuthResponseDTO> submitUserLogin(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(service.loginUsuario(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestParam String email) {
        service.forgotPassword(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        service.resetPassword(resetPasswordDTO);
        return ResponseEntity.noContent().build();
    }
}
