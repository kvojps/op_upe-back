package com.upe.observatorio.project.service;

import com.upe.observatorio.project.model.Curso;
import com.upe.observatorio.project.model.dto.CourseDTO;
import com.upe.observatorio.project.repository.CourseRepository;
import com.upe.observatorio.utils.ObservatoryException;
import com.upe.observatorio.utils.ProjectResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

	private final CourseRepository repository;

	public Curso createCourse(CourseDTO course) {
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

	public void updateCourse(CourseDTO course, Long id) throws ObservatoryException {
		if (repository.findById(id).isEmpty()) {
			throw new ProjectResourceNotFoundException("Curso not found");
		}
		
		Curso existentCourse = repository.findById(id).get();
		BeanUtils.copyProperties(course, existentCourse);
		
		repository.save(existentCourse);
	}

	public void deleteCourse(Long id) throws ObservatoryException {
		if (repository.findById(id).isEmpty()) {
			throw new ProjectResourceNotFoundException("Curso not found");
		}
		
		repository.deleteById(id);
	}
}
