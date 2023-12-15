package com.upe.observatorio.project.repository;

import com.upe.observatorio.project.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Curso, Long>{

}
