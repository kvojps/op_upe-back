package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.controlador.representacao.CampusCursoRepresentacao;
import com.upe.observatorio.projeto.dominio.dto.CampusCursoDTO;
import com.upe.observatorio.projeto.servico.CampusCursoServico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/campus-curso")
@CrossOrigin
@RequiredArgsConstructor
public class CampusCursoAPI {

    private final CampusCursoServico servico;

    @PostMapping
    public ResponseEntity<CampusCursoRepresentacao> adicionarCampusCurso(@RequestBody @Valid CampusCursoDTO campusCurso) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CampusCursoRepresentacao(servico.adicionarCampusCurso(campusCurso)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCampusCurso(@PathVariable("id") Long id) {
        servico.removerCampusCurso(id);

        return ResponseEntity.noContent().build();
    }
}
