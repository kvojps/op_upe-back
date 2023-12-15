package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.dto.CampusDTO;
import com.upe.observatorio.projeto.repositorio.CampusRepository;
import com.upe.observatorio.utils.ProjectResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampusServico {

	private final CampusRepository repositorio;

	public Campus adicionarCampus(CampusDTO campus) {
		Campus campusSalvar = new Campus();
		BeanUtils.copyProperties(campus, campusSalvar);

		return repositorio.save(campusSalvar);
	}

	public List<Campus> listarCampus() {
		return repositorio.findAll();
	}

	public Campus buscarCampusPorId(Long id) {
		return repositorio.findById(id).orElseThrow(() ->
				new ProjectResourceNotFoundException("Campus not found"));
	}

	public void atualizarCampus(CampusDTO campus, Long id) {
		if (repositorio.findById(id).isEmpty()) {
			throw new ProjectResourceNotFoundException("Campus not found");
		}

		Campus campusExistente = repositorio.findById(id).get();
		BeanUtils.copyProperties(campus, campusExistente);

		repositorio.save(campusExistente);
	}

	public void removerCampus(Long id) {
		if (repositorio.findById(id).isEmpty()) {
			throw new ProjectResourceNotFoundException("Campus not found");
		}

		repositorio.deleteById(id);
	}
}
