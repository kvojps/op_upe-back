package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.domain.Campus;
import com.upe.observatorio.projeto.domain.dto.CampusDTO;
import com.upe.observatorio.projeto.repository.CampusRepository;
import com.upe.observatorio.projeto.utilities.ProjetoException;

@Service
public class CampusServiceImpl implements CampusService {

	@Autowired
	private CampusRepository repositorio;

	@Override
	public List<Campus> listarCampus() {
		return repositorio.findAll();
	}

	@Override
	public Optional<Campus> buscarCampusPorId(Long id) {
		return repositorio.findById(id);
	}

	@Override
	public Campus adicionarCampus(CampusDTO campus) throws ProjetoException {
		ModelMapper modelMapper = new ModelMapper();
		Campus campusSalvar = modelMapper.map(campus, Campus.class);

		return repositorio.save(campusSalvar);
	}

	@Override
	public void atualizarCampus(CampusDTO campus, Long id) throws ProjetoException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ProjetoException("Não existe um campus associado a este id");
		}

		ModelMapper modelMapper = new ModelMapper();
		Campus campusAtualizar = modelMapper.map(campus, Campus.class);

		repositorio.save(campusAtualizar);

	}

	@Override
	public void removerCampus(Long id) throws ProjetoException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ProjetoException("Não existe um curso associado a este id");
		}

		repositorio.deleteById(id);

	}

	@Override
	public Map<String, Object> filtrarCampusPorNome(String nome, int pag, int tamanho) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> filtrarCampusPorCidade(String cidade, int pag, int tamanho) {
		// TODO Auto-generated method stub
		return null;
	}

}
