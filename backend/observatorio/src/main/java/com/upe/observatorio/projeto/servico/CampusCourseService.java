package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.CampusCurso;
import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.dto.CampusCursoDTO;
import com.upe.observatorio.projeto.repository.CampusCourseRepository;
import com.upe.observatorio.utils.RelationExistsException;
import com.upe.observatorio.utils.ProjectResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CampusCourseService {

	private final CampusCourseRepository repository;
	private final CampusService campusService;
	private final CourseService courseService;

	public CampusCurso createCampusCourse(CampusCursoDTO campusCourse)  {
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
