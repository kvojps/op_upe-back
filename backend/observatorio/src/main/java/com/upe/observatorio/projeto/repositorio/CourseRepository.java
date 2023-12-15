package com.upe.observatorio.projeto.repositorio;

import com.upe.observatorio.projeto.dominio.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Curso, Long>{

}
