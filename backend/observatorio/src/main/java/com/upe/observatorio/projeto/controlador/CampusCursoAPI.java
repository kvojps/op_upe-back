package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.controlador.representacao.CampusCursoRepresentacao;
import com.upe.observatorio.projeto.dominio.dto.CampusCursoDTO;
import com.upe.observatorio.projeto.service.CampusCourseService;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/campus-curso")
@CrossOrigin
@RequiredArgsConstructor
public class CampusCursoAPI {

    private final CampusCourseService servico;

    @PostMapping
    public ResponseEntity<CampusCursoRepresentacao> adicionarCampusCurso(
            @RequestBody @Valid CampusCursoDTO campusCurso,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new ObservatorioExcecao(String.join("; ", bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CampusCursoRepresentacao(servico.createCampusCourse(campusCurso)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCampusCurso(@PathVariable("id") Long id) {
        servico.deleteCampusCourse(id);

        return ResponseEntity.noContent().build();
    }
}
