package com.upe.observatorio.usuario.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuario")
@CrossOrigin
@RequiredArgsConstructor
public class UsuarioAPI {

//    private final UsuarioServico servico;
//
//    @GetMapping
//    public ResponseEntity<List<UsuarioRepresentacao>> listarUsuarios() {
//        return ResponseEntity
//                .ok(servico.listarUsuarios().stream().map(UsuarioRepresentacao::new).collect(Collectors.toList()));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable("id") Long id) {
//		ResponseEntity<?> resposta;
//		try {
//			Usuario usuario = servico.buscarUsuarioPorId(id).orElseThrow();
//			UsuarioRepresentacao resultado = new UsuarioRepresentacao(usuario);
//			resposta = ResponseEntity.ok(resultado);
//		} catch (ObservatorioExcecao e) {
//			resposta = ResponseEntity.badRequest().body(e.getMessage());
//		}
//
//		return resposta;
//    }
//
//    @PostMapping("/auth/cadastrar")
//    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody CadastroRequestDTO request) {
//        ResponseEntity<?> resposta;
//        try {
//            resposta = ResponseEntity.ok(servico.cadastrarUsuario(request));
//        } catch (ObservatorioExcecao e) {
//            resposta = ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//        return resposta;
//    }
//
//    @PostMapping("/auth/login")
//    public ResponseEntity<AutenticacaoResponseDTO> loginUsuario(@RequestBody AutenticacaoRequestDTO request) {
//        return ResponseEntity.ok(servico.loginUsuario(request));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> atualizarUsuario(@RequestBody @Valid UsuarioDTO usuario, @PathVariable Long id) {
//        try {
//            servico.atualizarUsuario(usuario, id);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> removerUsuario(@PathVariable("id") Long id) {
//        try {
//            servico.removerUsuario(id);
//        } catch (ObservatorioExcecao e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
}
