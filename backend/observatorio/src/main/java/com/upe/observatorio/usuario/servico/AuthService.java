package com.upe.observatorio.usuario.servico;

import com.upe.observatorio.config.JwtService;
import com.upe.observatorio.projeto.dominio.dto.EmailDTO;
import com.upe.observatorio.shared.EmailClient;
import com.upe.observatorio.usuario.dominio.Usuario;
import com.upe.observatorio.usuario.dominio.dto.AutenticacaoRequestDTO;
import com.upe.observatorio.usuario.dominio.dto.AutenticacaoResponseDTO;
import com.upe.observatorio.usuario.dominio.dto.ResetPasswordDTO;
import com.upe.observatorio.usuario.repositorio.UsuarioRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;
import com.upe.observatorio.utils.UserNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String SECRET_KEY = System.getenv("SECRET_KEY");

    private final UsuarioRepositorio repositorio;
    private final EmailClient emailClient;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoResponseDTO loginUsuario(AutenticacaoRequestDTO request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));

        Usuario usuario = repositorio.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(usuario);
        return AutenticacaoResponseDTO.builder().token(jwtToken).build();
    }

    public void forgotPassword(String email) {
        Map<String, Object> tokenPayload = new HashMap<>();
        tokenPayload.put("email", email);
        tokenPayload.put("type", "reset_password");
        tokenPayload.put("exp", new Date(System.currentTimeMillis() + 3600000));

        String jwtToken = Jwts.builder()
                .addClaims(tokenPayload)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        EmailDTO emailContent = new EmailDTO();
        emailContent.setSubject("Recuperação de senha");
        emailContent.setMessage("Token de recuperação de senha: " + jwtToken);
        emailContent.setReceiver(email);

        emailClient.sendEmail(emailContent);
    }

    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        Claims claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(resetPasswordDTO.getResetToken())
                    .getBody();
        } catch (Exception e) {
            throw new ObservatorioExcecao("Invalid JWT: " + e.getMessage());
        }

        String tokenUserEmail = claims.get("email", String.class);
        String tokenType = claims.get("type", String.class);

        if (!"reset_password".equals(tokenType)) {
            throw new ObservatorioExcecao("Invalid token_type");
        }

        UsuarioServico.validarSenha(resetPasswordDTO.getNewPassword());
        Usuario userToUpdate = repositorio.findByEmail(tokenUserEmail).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        userToUpdate.setSenha(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));

        repositorio.save(userToUpdate);
    }
}
