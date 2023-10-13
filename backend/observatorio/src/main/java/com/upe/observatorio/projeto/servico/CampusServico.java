package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.dto.CampusDTO;
import com.upe.observatorio.projeto.repositorio.CampusRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampusServico {

	private final CampusRepositorio repositorio;

	public Campus adicionarCampus(CampusDTO campus) {
		Campus campusSalvar = new Campus();
		BeanUtils.copyProperties(campus, campusSalvar);

		return repositorio.save(campusSalvar);
	}

	public List<Campus> listarCampus() {
		return repositorio.findAll();
	}

	public Campus buscarCampusPorId(Long id) throws ObservatorioExcecao {
		return repositorio.findById(id).orElseThrow(() ->
				new ObservatorioExcecao("Não existe um campus associado a este id "));
	}

	public void atualizarCampus(CampusDTO campus, Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um campus associado a este id");
		}

		Campus campusExistente = repositorio.findById(id).get();
		BeanUtils.copyProperties(campus, campusExistente);

		repositorio.save(campusExistente);
	}

	public void removerCampus(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um curso associado a este id");
		}

		repositorio.deleteById(id);
	}
}
