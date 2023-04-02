package com.upe.observatorio.projeto.controlador;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upe.observatorio.projeto.controlador.modelo.CursoProjetoRepresentacao;
import com.upe.observatorio.projeto.dominio.CursoProjeto;
import com.upe.observatorio.projeto.dominio.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.servico.CursoProjetoServico;
import com.upe.observatorio.utils.ObservatorioExcecao;

@RestController
@RequestMapping("api/curso-projeto")
@CrossOrigin
public class CursoProjetoAPI {

	@Autowired
	private CursoProjetoServico servico;

	@GetMapping
	public ResponseEntity<List<CursoProjetoRepresentacao>> listarCursoProjetos() {
		return ResponseEntity.ok(servico.listarCursoProjetos().stream().map(cursoProjeto -> convert(cursoProjeto))
				.collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CursoProjetoRepresentacao> buscarCursoProjetoPorId(@PathVariable("id") Long id) {
		CursoProjetoRepresentacao resultado = convert(servico.buscarCursoProjetoPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}

	@PostMapping
	public ResponseEntity<?> adicionarCursoProjeto(@RequestBody @Valid CursoProjetoDTO cursoProjeto) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			CursoProjetoRepresentacao resultado = modelMapper.map(servico.adicionarCursoProjeto(cursoProjeto),
					CursoProjetoRepresentacao.class);

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerCursoProjeto(@PathVariable("id") Long id) {
		try {
			servico.removerCursoProjeto(id);
		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	private CursoProjetoRepresentacao convert(CursoProjeto entidade) {
		ModelMapper modelMapper = new ModelMapper();
		CursoProjetoRepresentacao resultado = modelMapper.map(entidade, CursoProjetoRepresentacao.class);

		return resultado;
	}

}
