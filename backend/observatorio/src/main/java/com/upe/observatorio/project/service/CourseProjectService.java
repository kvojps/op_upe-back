package com.upe.observatorio.project.service;

import com.upe.observatorio.project.model.Curso;
import com.upe.observatorio.project.model.CursoProjeto;
import com.upe.observatorio.project.model.Projeto;
import com.upe.observatorio.project.model.dto.CourseProjectDTO;
import com.upe.observatorio.project.repository.CourseProjectRepository;
import com.upe.observatorio.utils.ProjectResourceNotFoundException;
import com.upe.observatorio.utils.RelationExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseProjectService {

    private final CourseProjectRepository repository;
    private final CourseService courseService;
    private final ProjectService projectService;

    public CursoProjeto createCourseProject(CourseProjectDTO courseProject) {
        Curso existentCourse = courseService.findCourseById(courseProject.getCursoId());
        Projeto existentProject = projectService.findProjectById(courseProject.getProjetoId());

        repository.findByCursoAndProjeto(existentCourse, existentProject).orElseThrow(() ->
                new RelationExistsException("Curso and projeto relation already exists"));


        CursoProjeto courseProjectToSave = new CursoProjeto();
        courseProjectToSave.setCurso(existentCourse);
        courseProjectToSave.setProjeto(existentProject);

        return repository.save(courseProjectToSave);
    }

    public void deleteCourseProject(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new ProjectResourceNotFoundException("Curso Projeto not found");
        }

        repository.deleteById(id);
    }
}
