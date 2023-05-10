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

import com.upe.observatorio.projeto.controlador.modelo.CampusCursoRepresentacao;
import com.upe.observatorio.projeto.dominio.CampusCurso;
import com.upe.observatorio.projeto.dominio.dto.CampusCursoDTO;
import com.upe.observatorio.projeto.servico.CampusCursoServico;
import com.upe.observatorio.utils.ObservatorioExcecao;

@RestController
@RequestMapping("api/campus-curso")
@CrossOrigin
public class CampusCursoAPI {

	@Autowired
	private CampusCursoServico servico;

	@GetMapping
	public ResponseEntity<List<CampusCursoRepresentacao>> listarCampusCurso() {
		return ResponseEntity
				.ok(servico.listarCampusCurso().stream().map(campus -> convert(campus)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CampusCursoRepresentacao> buscarCampusCursoPorId(@PathVariable("id") Long id)
			throws ObservatorioExcecao {
		CampusCursoRepresentacao resultado = convert(servico.buscarCampusCursoPorId(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}

	@PostMapping
	public ResponseEntity<?> adicionarCampusCurso(@RequestBody @Valid CampusCursoDTO campusCurso) {
		try {
			CampusCursoRepresentacao resultado = convert(servico.adicionarCampusCurso(campusCurso));

			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerCampusCurso(@PathVariable("id") Long id) {
		try {
			servico.removerCampusCurso(id);
		} catch (ObservatorioExcecao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	private CampusCursoRepresentacao convert(CampusCurso entidade) {
		ModelMapper modelMapper = new ModelMapper();
		CampusCursoRepresentacao resultado = modelMapper.map(entidade, CampusCursoRepresentacao.class);

		return resultado;
	}

}
