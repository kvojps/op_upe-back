package com.upe.observatorio.projeto.service;

import com.upe.observatorio.projeto.model.Campus;
import com.upe.observatorio.projeto.model.dto.CampusDTO;
import com.upe.observatorio.projeto.repository.CampusRepository;
import com.upe.observatorio.utils.ProjectResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampusService {

	private final CampusRepository repository;

	public Campus createCampus(CampusDTO campus) {
		Campus campusToSave = new Campus();
		BeanUtils.copyProperties(campus, campusToSave);

		return repository.save(campusToSave);
	}

	public List<Campus> readCampus() {
		return repository.findAll();
	}

	public Campus findCampusById(Long id) {
		return repository.findById(id).orElseThrow(() ->
				new ProjectResourceNotFoundException("Campus not found"));
	}

	public void updateCampus(CampusDTO campus, Long id) {
		if (repository.findById(id).isEmpty()) {
			throw new ProjectResourceNotFoundException("Campus not found");
		}

		Campus existentCampus = repository.findById(id).get();
		BeanUtils.copyProperties(campus, existentCampus);

		repository.save(existentCampus);
	}

	public void deleteCampus(Long id) {
		if (repository.findById(id).isEmpty()) {
			throw new ProjectResourceNotFoundException("Campus not found");
		}

		repository.deleteById(id);
	}
}
