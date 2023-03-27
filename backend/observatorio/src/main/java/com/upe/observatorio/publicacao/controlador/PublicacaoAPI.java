package com.upe.observatorio.publicacao.controlador;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.publicacao.controlador.modelo.PublicacaoRepresentacao;
import com.upe.observatorio.publicacao.dominio.Publicacao;
import com.upe.observatorio.publicacao.dominio.dto.PublicacaoDTO;
import com.upe.observatorio.publicacao.servico.PublicacaoServico;
import com.upe.observatorio.utils.ObservatorioExcecao;

@RestController
@RequestMapping("api/publicacao")
public class PublicacaoAPI {
	@Autowired
	private PublicacaoServico servico;

	@GetMapping
	public ResponseEntity<List<PublicacaoRepresentacao>> listarPublicacoes() {
		return ResponseEntity
				.ok(servico.listarPublicacoes().stream().map(projeto -> convert(projeto)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PublicacaoRepresentacao> buscarPublicacaoPorId(@PathVariable("id") Long id) {
		PublicacaoRepresentacao resultado = convert(servico.buscarPublicacaoPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}
	
	@PostMapping
	public ResponseEntity<?> adicionarPublicacao(@RequestBody @Valid PublicacaoDTO publicacao) {
		try {
			PublicacaoRepresentacao resultado = convert(servico.adicionarPublicacao(publicacao));

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerPublicacao(@PathVariable("id") Long id) {
		try {
			servico.removerPublicacao(id);
		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	private PublicacaoRepresentacao convert(Publicacao entidade) {
		ModelMapper modelMapper = new ModelMapper();
		PublicacaoRepresentacao resultado = modelMapper.map(entidade, PublicacaoRepresentacao.class);

		return resultado;
	}
}
