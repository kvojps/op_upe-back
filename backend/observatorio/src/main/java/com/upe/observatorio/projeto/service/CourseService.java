package com.upe.observatorio.projeto.service;

import com.upe.observatorio.projeto.model.Curso;
import com.upe.observatorio.projeto.model.dto.CursoDTO;
import com.upe.observatorio.projeto.repository.CourseRepository;
import com.upe.observatorio.utils.ObservatorioExcecao;
import com.upe.observatorio.utils.ProjectResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

	private final CourseRepository repository;

	public Curso createCourse(CursoDTO course) {
		Curso courseToSave = new Curso();
		BeanUtils.copyProperties(course, courseToSave);

		return repository.save(courseToSave);
	}

	public List<Curso> readCourses() {
		return repository.findAll();
	}

	public Curso findCourseById(Long id) {
		return repository.findById(id).orElseThrow(() ->
				new ProjectResourceNotFoundException("Curso not found"));
	}

	public void updateCourse(CursoDTO course, Long id) throws ObservatorioExcecao {
		if (repository.findById(id).isEmpty()) {
			throw new ProjectResourceNotFoundException("Curso not found");
		}
		
		Curso existentCourse = repository.findById(id).get();
		BeanUtils.copyProperties(course, existentCourse);
		
		repository.save(existentCourse);
	}

	public void deleteCourse(Long id) throws ObservatorioExcecao {
		if (repository.findById(id).isEmpty()) {
			throw new ProjectResourceNotFoundException("Curso not found");
		}
		
		repository.deleteById(id);
	}
}
