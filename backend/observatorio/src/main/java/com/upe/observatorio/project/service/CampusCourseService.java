package com.upe.observatorio.project.service;

import com.upe.observatorio.project.model.Campus;
import com.upe.observatorio.project.model.CampusCurso;
import com.upe.observatorio.project.model.Curso;
import com.upe.observatorio.project.model.dto.CampusCourseDTO;
import com.upe.observatorio.project.repository.CampusCourseRepository;
import com.upe.observatorio.utils.ProjectResourceNotFoundException;
import com.upe.observatorio.utils.RelationExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CampusCourseService {

	private final CampusCourseRepository repository;
	private final CampusService campusService;
	private final CourseService courseService;

	public CampusCurso createCampusCourse(CampusCourseDTO campusCourse)  {
		Campus existentCampus = campusService.findCampusById(campusCourse.getCampusId());
		Curso existentCourse = courseService.findCourseById(campusCourse.getCursoId());
		

		repository.findByCampusAndCurso(existentCampus, existentCourse).orElseThrow(() ->
				new RelationExistsException("Campus and curso relation already exists"));
		
		CampusCurso campusCourseToSave = new CampusCurso();
		campusCourseToSave.setCampus(existentCampus);
		campusCourseToSave.setCurso(existentCourse);

		return repository.save(campusCourseToSave);
	}

	public void deleteCampusCourse(Long id) {
		if (repository.findById(id).isEmpty()) {
			throw new ProjectResourceNotFoundException("CampusCurso not found");
		}

		repository.deleteById(id);
	}
}
