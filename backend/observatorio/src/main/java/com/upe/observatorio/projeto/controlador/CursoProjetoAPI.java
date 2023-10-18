package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.controlador.representacao.CursoProjetoRepresentacao;
import com.upe.observatorio.projeto.dominio.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.servico.CursoProjetoServico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/curso-projeto")
@CrossOrigin
@RequiredArgsConstructor
public class CursoProjetoAPI {

    private final CursoProjetoServico servico;

    @PostMapping
    public ResponseEntity<CursoProjetoRepresentacao> adicionarCursoProjeto(@RequestBody @Valid CursoProjetoDTO cursoProjeto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CursoProjetoRepresentacao(servico.adicionarCursoProjeto(cursoProjeto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCursoProjeto(@PathVariable("id") Long id) {
        servico.removerCursoProjeto(id);

        return ResponseEntity.noContent().build();
    }
}
