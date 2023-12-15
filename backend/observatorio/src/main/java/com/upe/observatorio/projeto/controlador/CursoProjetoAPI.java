package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.controlador.representacao.CursoProjetoRepresentacao;
import com.upe.observatorio.projeto.model.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.service.CourseProjectService;
import com.upe.observatorio.utils.ObservatorioExcecao;
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
public class CursoProjetoAPI {

    private final CourseProjectService servico;

    @PostMapping
    public ResponseEntity<CursoProjetoRepresentacao> adicionarCursoProjeto(
            @RequestBody @Valid CursoProjetoDTO cursoProjeto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new ObservatorioExcecao(String.join("; ", bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CursoProjetoRepresentacao(servico.createCourseProject(cursoProjeto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCursoProjeto(@PathVariable("id") Long id) {
        servico.deleteCourseProject(id);

        return ResponseEntity.noContent().build();
    }
}
