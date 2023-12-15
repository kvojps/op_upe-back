package com.upe.observatorio.projeto.service;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.dto.ProjetoDTO;
import com.upe.observatorio.projeto.dominio.dto.ProjetoFiltroDTO;
import com.upe.observatorio.projeto.repository.ProjectRepository;
import com.upe.observatorio.usuario.dominio.Usuario;
import com.upe.observatorio.usuario.servico.UsuarioServico;
import com.upe.observatorio.utils.ProjectResourceNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;
    private final UsuarioServico userService;
    private final CampusService campusService;

    public Projeto createProject(ProjetoDTO project) {
        Projeto projectToSave = new Projeto();
        BeanUtils.copyProperties(project, projectToSave);

        Usuario existentUser = userService.buscarUsuarioPorId(project.getUsuarioId());
        Campus existentCampus = campusService.findCampusById(project.getCampusId());
        projectToSave.setUsuario(existentUser);
        projectToSave.setCampus(existentCampus);

        return repository.save(projectToSave);
    }

    public Page<Projeto> readProjects(ProjetoFiltroDTO dto, int page, int size) {
        Pageable requestedPage = PageRequest.of(page, size);
        return repository.findWithFilters(dto.getDataInicio(), dto.getDataFim(),
                dto.getTitulo(), dto.getAreaTematica(), dto.getModalidade(), requestedPage);
    }

    public Projeto findProjectById(@NotNull Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ProjectResourceNotFoundException("Project not found"));
    }

    public void updateProject(ProjetoDTO project, @NotNull Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new ProjectResourceNotFoundException("Project not found");
        }

        Projeto existentProject = repository.findById(id).get();
        BeanUtils.copyProperties(project, existentProject);

        repository.save(existentProject);
    }

    public void deleteProject(@NotNull Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new ProjectResourceNotFoundException("Project not found");
        }

        repository.deleteById(id);
    }
}
