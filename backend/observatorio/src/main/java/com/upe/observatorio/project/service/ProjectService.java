package com.upe.observatorio.project.service;

import com.upe.observatorio.project.model.Campus;
import com.upe.observatorio.project.model.Projeto;
import com.upe.observatorio.project.model.dto.ProjectDTO;
import com.upe.observatorio.project.model.dto.ProjectFilterDTO;
import com.upe.observatorio.project.repository.ProjectRepository;
import com.upe.observatorio.user.model.Usuario;
import com.upe.observatorio.user.service.UserService;
import com.upe.observatorio.utils.ObservatoryException;
import com.upe.observatorio.utils.ProjectResourceNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;
    private final UserService userService;
    private final CampusService campusService;

    public Projeto createProject(ProjectDTO project) {
        Projeto projectToSave = new Projeto();
        BeanUtils.copyProperties(project, projectToSave);

        Usuario existentUser = userService.findUserById(project.getUsuarioId());
        Campus existentCampus = campusService.findCampusById(project.getCampusId());
        projectToSave.setUsuario(existentUser);
        projectToSave.setCampus(existentCampus);

        return repository.save(projectToSave);
    }

    public Page<Projeto> readProjects(ProjectFilterDTO dto, int page, int size) {
        Pageable requestedPage = PageRequest.of(page, size);
        return repository.findWithFilters(dto.getDataInicio(), dto.getDataFim(),
                dto.getTitulo(), dto.getAreaTematica(), dto.getModalidade(), requestedPage);
    }

    public Projeto findProjectById(@NotNull Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ProjectResourceNotFoundException("Project not found"));
    }

    public void updateProject(ProjectDTO project, @NotNull Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new ProjectResourceNotFoundException("Project not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) authentication.getPrincipal();
        Projeto existentProject = repository.findById(id).get();
        if (!existentProject.getUsuario().getEmail().equals(user.getEmail())) {
            throw new ObservatoryException("User not permitted to do this action");
        }

        BeanUtils.copyProperties(project, existentProject);

        repository.save(existentProject);
    }

    public void deleteProject(@NotNull Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new ProjectResourceNotFoundException("Project not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) authentication.getPrincipal();
        Projeto existentProject = repository.findById(id).get();
        if (!existentProject.getUsuario().getEmail().equals(user.getEmail())) {
            throw new ObservatoryException("User not permitted to do this action");
        }

        repository.deleteById(id);
    }
}
