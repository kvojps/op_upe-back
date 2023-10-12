package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.controlador.modelo.ProjetoRepresentacao;
import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.dto.ProjetoDTO;
import com.upe.observatorio.projeto.dominio.dto.ProjetoFiltroDTO;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import com.upe.observatorio.projeto.servico.PlanilhaServico;
import com.upe.observatorio.projeto.servico.ProjetoServico;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/projetos")
@CrossOrigin
@RequiredArgsConstructor
public class ProjetoAPI {

    private final ProjetoServico servico;
    private final PlanilhaServico planilhaServico;

    @PostMapping
    public ResponseEntity<?> adicionarProjeto(@RequestBody @Valid ProjetoDTO projeto) {
        try {
            ProjetoRepresentacao resultado = new ProjetoRepresentacao(servico.adicionarProjeto(projeto));

            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

        } catch (ObservatorioExcecao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> listarProjetos(
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "areaTematica", required = false) AreaTematicaEnum areaTematica,
            @RequestParam(value = "modalidade", required = false) ModalidadeEnum modalidade,
            @RequestParam(value = "dataInicio", required = false) String dataInicio,
            @RequestParam(value = "dataFim", required = false) String dataFim,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        ProjetoFiltroDTO dto = ProjetoFiltroDTO.builder().titulo(titulo).areaTematica(areaTematica).
                modalidade(modalidade).dataInicio(dataInicio).dataFim(dataFim).page(page).size(size).build();
        Page<Projeto> projetosPagina = servico.listarProjetos(dto, page, size);
        Map<String, Object> resposta = gerarPaginacao(projetosPagina);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/privados")
    public ResponseEntity<List<ProjetoRepresentacao>> listarProjetosPrivadosPorUsuario(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) throws ObservatorioExcecao {
        return ResponseEntity.ok(servico.listarProjetosPrivados(id, page, size).stream()
                .map(ProjetoRepresentacao::new).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProjetoPorId(@PathVariable("id") Long id) {
        ResponseEntity<?> resposta;
        try {
            Projeto projeto = servico.buscarProjetoPorId(id);
            ProjetoRepresentacao resultado = new ProjetoRepresentacao(projeto);
            resposta = ResponseEntity.ok(resultado);
        } catch (ObservatorioExcecao e) {
            resposta = ResponseEntity.badRequest().body(e.getMessage());
        }

        return resposta;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProjeto(@RequestBody @Valid ProjetoDTO projeto, @PathVariable Long id) {
        try {
            servico.atualizarProjeto(projeto, id);
        } catch (ObservatorioExcecao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerProjeto(@PathVariable("id") Long id) {
        try {
            servico.removerProjeto(id);
        } catch (ObservatorioExcecao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/planilha")
    public void carregarProjetoPlanilhas(@RequestPart MultipartFile planilha) {
        try {
            planilhaServico.carregarProjetosPlanilha(planilha);
        } catch (IOException | ObservatorioExcecao e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> gerarPaginacao(Page<Projeto> projetosPagina) {
        List<ProjetoRepresentacao> projetosContent = projetosPagina.getContent().stream().map(ProjetoRepresentacao::new).collect(Collectors.toList());

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("projetos", projetosContent);
        resposta.put("paginaAtual", projetosPagina.getNumber());
        resposta.put("totalItens", projetosPagina.getTotalElements());
        resposta.put("totalPaginas", projetosPagina.getTotalPages());

        return resposta;
    }
}
