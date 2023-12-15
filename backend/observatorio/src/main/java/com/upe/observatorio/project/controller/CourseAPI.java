package com.upe.observatorio.project.controller;

import com.upe.observatorio.project.controller.response.CourseResponse;
import com.upe.observatorio.project.model.Curso;
import com.upe.observatorio.project.model.dto.CourseDTO;
import com.upe.observatorio.project.service.CourseService;
import com.upe.observatorio.utils.ObservatoryException;
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
public class CourseAPI {

	private final CourseService service;

	@PostMapping
	public ResponseEntity<CourseResponse> createCourse(
			@RequestBody @Valid CourseDTO course,
			BindingResult bindingResult
			) {
		if (bindingResult.hasErrors()) {
			throw new ObservatoryException(String.join("; ", bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(new CourseResponse(service.createCourse(course)));
	}

	@GetMapping
	public ResponseEntity<List<CourseResponse>> readCourses() {
		return ResponseEntity
				.ok(service.readCourses().stream().map(CourseResponse::new).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CourseResponse> findCourseById(@PathVariable("id") Long id) {
		Curso curso = service.findCourseById(id);

		return ResponseEntity.ok(new CourseResponse(curso));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateCourse(
			@RequestBody @Valid CourseDTO course,
			@PathVariable Long id,
			BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			throw new ObservatoryException(String.join("; ", bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
		}

		service.updateCourse(course, id);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long id) {
		service.deleteCourse(id);

		return ResponseEntity.noContent().build();
	}
}
