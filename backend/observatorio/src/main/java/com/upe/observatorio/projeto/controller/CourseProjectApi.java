package com.upe.observatorio.projeto.controller;

import com.upe.observatorio.projeto.controller.response.CourseProjectResponse;
import com.upe.observatorio.projeto.model.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.service.CourseProjectService;
import com.upe.observatorio.utils.ObservatoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/curso-projeto")
@CrossOrigin
@RequiredArgsConstructor
public class CourseProjectApi {

    private final CourseProjectService service;

    @PostMapping
    public ResponseEntity<CourseProjectResponse> createCourseProject(
            @RequestBody @Valid CursoProjetoDTO courseProject,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new ObservatoryException(String.join("; ", bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CourseProjectResponse(service.createCourseProject(courseProject)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseProject(@PathVariable("id") Long id) {
        service.deleteCourseProject(id);

        return ResponseEntity.noContent().build();
    }
}
