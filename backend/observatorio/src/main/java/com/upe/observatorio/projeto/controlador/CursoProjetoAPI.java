package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.controlador.modelo.CursoProjetoRepresentacao;
import com.upe.observatorio.projeto.dominio.CursoProjeto;
import com.upe.observatorio.projeto.dominio.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.servico.CursoProjetoServico;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/curso-projeto")
@CrossOrigin
@RequiredArgsConstructor
public class CursoProjetoAPI {

    private final CursoProjetoServico servico;

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
}
