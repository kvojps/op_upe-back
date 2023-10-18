package com.upe.observatorio.projeto.controlador;

import com.upe.observatorio.projeto.controlador.representacao.ProjetoRepresentacao;
import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.dto.ProjetoDTO;
import com.upe.observatorio.projeto.dominio.dto.ProjetoFiltroDTO;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import com.upe.observatorio.projeto.dominio.envelopes.StatusExecucaoVO;
import com.upe.observatorio.projeto.servico.PlanilhaServico;
import com.upe.observatorio.projeto.servico.ProjetoServico;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
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
    public ResponseEntity<ProjetoRepresentacao> adicionarProjeto(
            @RequestBody @Valid ProjetoDTO projeto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new ObservatorioExcecao(String.join("; ", bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).
                body(new ProjetoRepresentacao(servico.adicionarProjeto(projeto)));
    }

    @PostMapping("/planilha")
    public ResponseEntity<List<StatusExecucaoVO>> carregarProjetoPlanilhas(@RequestPart MultipartFile planilha) {
        List<StatusExecucaoVO> statusExec = planilhaServico.carregarProjetosPlanilha(planilha);

        return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(statusExec);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarProjetos(
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "areaTematica", required = false) AreaTematicaEnum areaTematica,
            @RequestParam(value = "modalidade", required = false) ModalidadeEnum modalidade,
            @RequestParam(value = "dataInicio", required = false) String dataInicio,
            @RequestParam(value = "dataFim", required = false) String dataFim,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        ProjetoFiltroDTO filtro = ProjetoFiltroDTO.builder().titulo(titulo).areaTematica(areaTematica).
                modalidade(modalidade).dataInicio(dataInicio).dataFim(dataFim).page(page).size(size).build();
        Page<Projeto> projetosPagina = servico.listarProjetos(filtro, page, size);

        return ResponseEntity.ok(gerarPaginacao(projetosPagina));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoRepresentacao> buscarProjetoPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ProjetoRepresentacao(servico.buscarProjetoPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarProjeto(
            @RequestBody @Valid ProjetoDTO projeto,
            @PathVariable Long id,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new ObservatorioExcecao(String.join("; ", bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
        }

        servico.atualizarProjeto(projeto, id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProjeto(@PathVariable("id") Long id) {
        servico.removerProjeto(id);

        return ResponseEntity.noContent().build();
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
