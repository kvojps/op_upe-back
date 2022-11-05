package com.upe.observatorio.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.domain.Campus;

public interface CampusRepository extends JpaRepository<Campus, Long> {

}
