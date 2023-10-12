package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.dto.CampusDTO;
import com.upe.observatorio.projeto.repositorio.CampusRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CampusServico {

	private final CampusRepositorio repositorio;

	public List<Campus> listarCampus() {
		return repositorio.findAll();
	}

	public Campus buscarCampusPorId(Long id) throws ObservatorioExcecao {
		return repositorio.findById(id).orElseThrow(() ->
				new ObservatorioExcecao("Não existe um campus associado a este id "));
	}

	public Campus adicionarCampus(CampusDTO campus) throws ObservatorioExcecao {
		ModelMapper modelMapper = new ModelMapper();
		Campus campusSalvar = modelMapper.map(campus, Campus.class);

		return repositorio.save(campusSalvar);
	}

	public void atualizarCampus(CampusDTO campus, Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um campus associado a este id");
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

	public void removerCampus(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um curso associado a este id");
		}

		repositorio.deleteById(id);
	}
}
