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
@RequestMapping("api/projeto")
@CrossOrigin
@RequiredArgsConstructor
public class ProjetoAPI {

    private final ProjetoServico servico;
    private final PlanilhaServico planilhaServico;

    @GetMapping
    public ResponseEntity<List<ProjetoRepresentacao>> listarProjetos() {
        return ResponseEntity
                .ok(servico.listarProjetos().stream().map(ProjetoRepresentacao::new).collect(Collectors.toList()));
    }

    @GetMapping("/privado")
    public ResponseEntity<List<ProjetoRepresentacao>> listarProjetosPrivados() {
        return ResponseEntity.ok(servico.listarProjetosPrivados().stream().map(ProjetoRepresentacao::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/privado/{id}")
    public ResponseEntity<List<ProjetoRepresentacao>> listarProjetosPrivadosPorUsuario(@PathVariable("id") Long id)
            throws ObservatorioExcecao {
        return ResponseEntity.ok(servico.listarProjetosPrivadosPorUsuario(id).stream().map(ProjetoRepresentacao::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/paginado")
    public ResponseEntity<Map<String, Object>> listarProjetosPaginado(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<Projeto> projetosPagina = servico.listarProjetosPaginado(page, size);
        Map<String, Object> resposta = gerarPaginacao(projetosPagina);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProjetoPorId(@PathVariable("id") Long id) {
		ResponseEntity<?> resposta;
		try {
			Projeto projeto = servico.buscarProjetoPorId(id).orElseThrow();
            ProjetoRepresentacao resultado = new ProjetoRepresentacao(projeto);
			resposta = ResponseEntity.ok(resultado);
		} catch (ObservatorioExcecao e) {
			resposta = ResponseEntity.badRequest().body(e.getMessage());
		}

		return resposta;
    }

    @PostMapping
    public ResponseEntity<?> adicionarProjeto(@RequestBody @Valid ProjetoDTO projeto) {
        try {
            ProjetoRepresentacao resultado = new ProjetoRepresentacao(servico.adicionarProjeto(projeto));

            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

        } catch (ObservatorioExcecao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

    @GetMapping("/filtro")
    public ResponseEntity<Map<String, Object>> filtrarProjetoComTodosFiltros(
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "areaTematica", required = false) AreaTematicaEnum areaTematica,
            @RequestParam(value = "modalidade", required = false) ModalidadeEnum modalidade,
            @RequestParam(value = "dataInicio", required = false) String dataInicio,
            @RequestParam(value = "dataFim", required = false) String dataFim,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        ProjetoFiltroDTO dto = ProjetoFiltroDTO.builder().titulo(titulo).areaTematica(areaTematica)
                .modalidade(modalidade).dataInicio(dataInicio).dataFim(dataFim).page(page).size(size).build();
        Page<Projeto> projetosPagina = servico.filtrarProjetoComTodosFiltros(dto, page, size);
        Map<String, Object> resposta = gerarPaginacao(projetosPagina);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/filtro/titulo")
    public ResponseEntity<Map<String, Object>> filtrarProjetoPorTitulo(
            @RequestParam(value = "titulo") String titulo,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<Projeto> projetosPagina = servico.filtrarProjetoPorTitulo(titulo, page, size);
        Map<String, Object> resposta = gerarPaginacao(projetosPagina);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/recentes")
    public ResponseEntity<List<ProjetoRepresentacao>> filtrarProjetosRecentes() {
        return ResponseEntity.ok(servico.filtrarProjetosRecentes().stream().map(ProjetoRepresentacao::new)
                .collect(Collectors.toList()));
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
        List<ProjetoRepresentacao> projetosContent = projetosPagina.getContent().stream()
                .map(ProjetoRepresentacao::new).collect(Collectors.toList());

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("projetos", projetosContent);
        resposta.put("paginaAtual", projetosPagina.getNumber());
        resposta.put("totalItens", projetosPagina.getTotalElements());
        resposta.put("totalPaginas", projetosPagina.getTotalPages());

        return resposta;
    }
}
