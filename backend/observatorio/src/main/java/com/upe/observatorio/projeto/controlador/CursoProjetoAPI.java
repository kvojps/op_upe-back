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

    @GetMapping
    public ResponseEntity<List<CursoProjetoRepresentacao>> listarCursoProjetos() {
        return ResponseEntity.ok(servico.listarCursoProjetos().stream().map(CursoProjetoRepresentacao::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCursoProjetoPorId(@PathVariable("id") Long id) {
        ResponseEntity<?> resposta;
        try {
            CursoProjeto cursoProjeto = servico.buscarCursoProjetoPorId(id).orElseThrow();
            CursoProjetoRepresentacao resultado = new CursoProjetoRepresentacao(cursoProjeto);
            resposta = ResponseEntity.ok(resultado);
        } catch (ObservatorioExcecao e) {
            resposta = ResponseEntity.badRequest().body(e.getMessage());
        }

        return resposta;
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
}
