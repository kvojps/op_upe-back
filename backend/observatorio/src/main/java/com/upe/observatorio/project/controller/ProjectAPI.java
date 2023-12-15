package com.upe.observatorio.project.controller;

import com.upe.observatorio.project.controller.response.ProjectResponse;
import com.upe.observatorio.project.model.Projeto;
import com.upe.observatorio.project.model.dto.ProjectDTO;
import com.upe.observatorio.project.model.dto.ProjectFilterDTO;
import com.upe.observatorio.project.model.enums.ModalityEnum;
import com.upe.observatorio.project.model.enums.ThematicAreaEnum;
import com.upe.observatorio.project.service.ProjectService;
import com.upe.observatorio.utils.ObservatoryException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @RequestBody @Valid ProjectDTO projectDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new ObservatoryException(String.join("; ", bindingResult.getAllErrors().stream()
                    .map(ObjectError::toString).toList()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).
                body(new ProjectResponse(service.createProject(projectDTO)));
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> readProjects(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "thematicArea", required = false) ThematicAreaEnum thematicArea,
            @RequestParam(value = "modality", required = false) ModalityEnum modality,
            @RequestParam(value = "initialDate", required = false) String initialDate,
            @RequestParam(value = "finalDate", required = false) String finalDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        ProjectFilterDTO projectFilter = ProjectFilterDTO.builder().titulo(title).areaTematica(thematicArea).
                modalidade(modality).dataInicio(initialDate).dataFim(finalDate).page(page).size(size).build();
        Page<Projeto> projectsPage = service.readProjects(projectFilter, page, size);

        return ResponseEntity.ok(getPagination(projectsPage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> findProjectById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ProjectResponse(service.findProjectById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProject(
            @RequestBody @Valid ProjectDTO project,
            @PathVariable Long id,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new ObservatoryException(String.join("; ", bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
        }

        service.updateProject(project, id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") Long id) {
        service.deleteProject(id);

        return ResponseEntity.noContent().build();
    }

    private Map<String, Object> getPagination(Page<Projeto> projetosPagina) {
        List<ProjectResponse> projectsContent = projetosPagina.getContent().stream().map(ProjectResponse::new).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("projetos", projectsContent);
        response.put("paginaAtual", projetosPagina.getNumber());
        response.put("totalItens", projetosPagina.getTotalElements());
        response.put("totalPaginas", projetosPagina.getTotalPages());

        return response;
    }
}
