package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.controlador.representacao.CursoRepresentacao;
import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.dto.CursoDTO;
import com.upe.observatorio.projeto.service.CourseService;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/cursos")
@CrossOrigin
@RequiredArgsConstructor
public class CursoAPI {

	private final CourseService servico;

	@PostMapping
	public ResponseEntity<CursoRepresentacao> adicionarCurso(
			@RequestBody @Valid CursoDTO curso,
			BindingResult bindingResult
			) {
		if (bindingResult.hasErrors()) {
			throw new ObservatorioExcecao(String.join("; ", bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(new CursoRepresentacao(servico.createCourse(curso)));
	}

	@GetMapping
	public ResponseEntity<List<CursoRepresentacao>> listarCursos() {
		return ResponseEntity
				.ok(servico.readCourses().stream().map(CursoRepresentacao::new).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CursoRepresentacao> buscarCursoPorId(@PathVariable("id") Long id) {
		Curso curso = servico.findCourseById(id);

		return ResponseEntity.ok(new CursoRepresentacao(curso));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizarCurso(
			@RequestBody @Valid CursoDTO curso,
			@PathVariable Long id,
			BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			throw new ObservatorioExcecao(String.join("; ", bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
		}

		servico.updateCourse(curso, id);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removerCurso(@PathVariable("id") Long id) {
		servico.deleteCourse(id);

		return ResponseEntity.noContent().build();
	}
}
