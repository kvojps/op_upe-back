package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.domain.Campus;
import com.upe.observatorio.projeto.domain.dto.CampusDTO;
import com.upe.observatorio.projeto.repository.CampusRepository;
import com.upe.observatorio.projeto.utilities.ObservatorioException;

@Service
public class CampusService {

	@Autowired
	private CampusRepository repositorio;

	public List<Campus> listarCampus() {
		return repositorio.findAll();
	}

	public Optional<Campus> buscarCampusPorId(Long id) {
		return repositorio.findById(id);
	}

	public Campus adicionarCampus(CampusDTO campus) throws ObservatorioException {
		ModelMapper modelMapper = new ModelMapper();
		Campus campusSalvar = modelMapper.map(campus, Campus.class);

		return repositorio.save(campusSalvar);
	}

	public void atualizarCampus(CampusDTO campus, Long id) throws ObservatorioException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioException("Não existe um campus associado a este id");
		}

		Campus campusExistente = repositorio.findById(id).get();
		if (!campusExistente.getNome().equals(campus.getNome())) {
			campusExistente.setNome(campus.getNome());
		}
		if (!campusExistente.getCidade().equals(campus.getCidade())) {
			campusExistente.setCidade(campus.getCidade());
		}
		if (!campusExistente.getBairro().equals(campus.getBairro())) {
			campusExistente.setBairro(campus.getBairro());
		}
		if (!campusExistente.getRua().equals(campus.getRua())) {
			campusExistente.setRua(campus.getRua());
		}

		repositorio.save(campusExistente);
	}

	public void removerCampus(Long id) throws ObservatorioException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioException("Não existe um curso associado a este id");
		}

		repositorio.deleteById(id);

	}

	public Page<Campus> filtrarCampusPorNome(String nome, Pageable pageable) {
		return repositorio.findAllByNomeContainingIgnoreCase(nome, pageable);
	}

	public Page<Campus> filtrarCampusPorCidade(String cidade, Pageable pageable) {
		return repositorio.findAllByCidadeContainingIgnoreCase(cidade, pageable);
	}

}
