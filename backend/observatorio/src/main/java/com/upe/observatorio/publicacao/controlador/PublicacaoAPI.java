package com.upe.observatorio.publicacao.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/publicacao")
@CrossOrigin
@RequiredArgsConstructor
public class PublicacaoAPI {

//	private final PublicacaoServico servico;

//	@GetMapping
//	public ResponseEntity<List<PublicacaoRepresentacao>> listarPublicacoes() {
//		return ResponseEntity
//				.ok(servico.listarPublicacoes().stream().map(PublicacaoRepresentacao::new).collect(Collectors.toList()));
//	}
//
//	@GetMapping("/semelhantes")
//	public ResponseEntity<List<PublicacaoRepresentacao>> listarPublicacoesSemelhantes(
//			@RequestParam(value = "areaTematica") AreaTematicaEnum areaTematica) {
//		return ResponseEntity.ok(servico.listarPublicacoesSemelhantes(areaTematica).stream()
//				.map(PublicacaoRepresentacao::new).collect(Collectors.toList()));
//	}
//
//	@GetMapping("/{id}")
//	public ResponseEntity<?> buscarPublicacaoPorId(@PathVariable("id") Long id) {
//		ResponseEntity<?> resposta;
//		try {
//			Publicacao publicacao = servico.buscarPublicacaoPorId(id).orElseThrow();
//			PublicacaoRepresentacao resultado = new PublicacaoRepresentacao(publicacao);
//			resposta = ResponseEntity.ok(resultado);
//		} catch (ObservatorioExcecao e) {
//			resposta = ResponseEntity.badRequest().body(e.getMessage());
//		}
//
//		return resposta;
//	}
//
//	@PostMapping
//	public ResponseEntity<?> adicionarPublicacao(@RequestBody @Valid PublicacaoDTO publicacao) {
//		try {
//			PublicacaoRepresentacao resultado = new PublicacaoRepresentacao(servico.adicionarPublicacao(publicacao));
//
//			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
//
//		} catch (ObservatorioExcecao e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> removerPublicacao(@PathVariable("id") Long id) {
//		try {
//			servico.removerPublicacao(id);
//		} catch (ObservatorioExcecao e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//
//		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//	}
//
//	@PostMapping("/curtida/add/{id}")
//	public ResponseEntity<?> adicionarCurtida(@PathVariable("id") Long id) throws ObservatorioExcecao {
//		servico.adicionarCurtida(id);
//
//		return ResponseEntity.ok("Curtida adicionada!");
//	}
//
//	@PostMapping("/descurtida/add/{id}")
//	public ResponseEntity<?> adicionarDescurtida(@PathVariable("id") Long id) throws ObservatorioExcecao {
//		servico.adicionarDescurtida(id);
//
//		return ResponseEntity.ok("Curtida adicionada!");
//	}
//
//	@PostMapping("/curtida/rm/{id}")
//	public ResponseEntity<?> removerCurtida(@PathVariable("id") Long id) throws ObservatorioExcecao {
//		servico.removerCurtida(id);
//
//		return ResponseEntity.ok("Curtida removida!");
//	}
//
//	@PostMapping("/descurtida/rm/{id}")
//	public ResponseEntity<?> removerDescurtida(@PathVariable("id") Long id) throws ObservatorioExcecao {
//		servico.removerDescurtida(id);
//
//		return ResponseEntity.ok("Curtida removida!");
//	}
}
