package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.controlador.modelo.CampusCursoRepresentacao;
import com.upe.observatorio.projeto.dominio.CampusCurso;
import com.upe.observatorio.projeto.servico.CampusCursoServico;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/campus-curso")
@CrossOrigin
@RequiredArgsConstructor
public class CampusCursoAPI {

    private final CampusCursoServico servico;

    @GetMapping
    public ResponseEntity<List<CampusCursoRepresentacao>> listarCampusCurso() {
        return ResponseEntity
                .ok(servico.listarCampusCurso().stream().map(CampusCursoRepresentacao::new).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCampusCursoPorId(@PathVariable("id") Long id) {
		ResponseEntity<?> resposta;
		try {
			CampusCurso campus = servico.buscarCampusCursoPorId(id).orElseThrow();
			CampusCursoRepresentacao resultado = new CampusCursoRepresentacao(campus);
			resposta = ResponseEntity.ok(resultado);
		} catch (ObservatorioExcecao e) {
			resposta = ResponseEntity.badRequest().body(e.getMessage());
		}

		return resposta;
    }

//    @PostMapping
//    public ResponseEntity<?> adicionarCampusCurso(@RequestBody @Valid CampusCursoDTO campusCurso) {
//        try {
//            CampusCursoRepresentacao resultado = new CampusCursoRepresentacao(servico.adicionarCampusCurso(campusCurso));
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
//
//        } catch (ObservatorioExcecao e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> removerCampusCurso(@PathVariable("id") Long id) {
//        try {
//            servico.removerCampusCurso(id);
//        } catch (ObservatorioExcecao e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
}
