package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.CursoProjeto;
import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.repository.CourseProjectRepository;
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

    public CursoProjeto createCourseProject(CursoProjetoDTO courseProject) {
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