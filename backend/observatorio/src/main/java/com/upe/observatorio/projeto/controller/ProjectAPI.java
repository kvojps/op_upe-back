package com.upe.observatorio.projeto.controller;

import com.upe.observatorio.projeto.controller.response.ProjectResponse;
import com.upe.observatorio.projeto.model.Projeto;
import com.upe.observatorio.projeto.model.dto.ProjetoDTO;
import com.upe.observatorio.projeto.model.dto.ProjetoFiltroDTO;
import com.upe.observatorio.projeto.model.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.model.enums.ModalidadeEnum;
import com.upe.observatorio.projeto.model.vos.StatusExecucaoVO;
import com.upe.observatorio.projeto.service.SheetService;
import com.upe.observatorio.projeto.service.ProjectService;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
public class ProjectAPI {

    private final ProjectService service;
    private final SheetService sheetService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @RequestBody @Valid ProjetoDTO projectDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new ObservatorioExcecao(String.join("; ", bindingResult.getAllErrors().stream()
                    .map(ObjectError::toString).toList()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).
                body(new ProjectResponse(service.createProject(projectDTO)));
    }

    @PostMapping("/planilha")
    public ResponseEntity<List<StatusExecucaoVO>> batchCreateProjects(@RequestPart MultipartFile sheet) {
        List<StatusExecucaoVO> statusExec = sheetService.batchCreateProjects(sheet);

        return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(statusExec);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> readProjects(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "thematicArea", required = false) AreaTematicaEnum thematicArea,
            @RequestParam(value = "modality", required = false) ModalidadeEnum modality,
            @RequestParam(value = "initialDate", required = false) String initialDate,
            @RequestParam(value = "finalDate", required = false) String finalDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        ProjetoFiltroDTO projectFilter = ProjetoFiltroDTO.builder().titulo(title).areaTematica(thematicArea).
                modalidade(modality).dataInicio(initialDate).dataFim(finalDate).page(page).size(size).build();
        Page<Projeto> projectsPage = service.readProjects(projectFilter, page, size);

        return ResponseEntity.ok(gerarPaginacao(projectsPage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> findProjectById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ProjectResponse(service.findProjectById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProject(
            @RequestBody @Valid ProjetoDTO project,
            @PathVariable Long id,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new ObservatorioExcecao(String.join("; ", bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
        }

        service.updateProject(project, id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProjeto(@PathVariable("id") Long id) {
        service.deleteProject(id);

        return ResponseEntity.noContent().build();
    }

    private Map<String, Object> gerarPaginacao(Page<Projeto> projetosPagina) {
        List<ProjectResponse> projetosContent = projetosPagina.getContent().stream().map(ProjectResponse::new).collect(Collectors.toList());

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("projetos", projetosContent);
        resposta.put("paginaAtual", projetosPagina.getNumber());
        resposta.put("totalItens", projetosPagina.getTotalElements());
        resposta.put("totalPaginas", projetosPagina.getTotalPages());

        return resposta;
    }
}
