package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.domain.Projeto;
import com.upe.observatorio.projeto.domain.dto.ProjetoDTO;
import com.upe.observatorio.projeto.repository.ProjetoRepository;
import com.upe.observatorio.projeto.utilities.ProjetoException;

@Service
public class ProjetoService {

	@Autowired
	private ProjetoRepository repositorio;


	public List<Projeto> listarProjetos() {
		return repositorio.findAll();
	}

	public Optional<Projeto> buscarProjetoPorId(Long id) {
		return repositorio.findById(id);
	}

	public Projeto adicionarProjeto(ProjetoDTO projeto) throws ProjetoException {
		ModelMapper modelMapper = new ModelMapper();
		Projeto projetoSalvar = modelMapper.map(projeto, Projeto.class);

		return repositorio.save(projetoSalvar);
	}

	public void atualizarProjeto(ProjetoDTO projeto, Long id) throws ProjetoException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ProjetoException("Não existe um projeto associado a este id");
		}
		
		ModelMapper modelMapper = new ModelMapper();
		Projeto projetoAtualizar = modelMapper.map(projeto, Projeto.class);
		
		repositorio.save(projetoAtualizar);

	}

	public void removerProjeto(Long id) throws ProjetoException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ProjetoException("Não existe um projeto associado a este id");
		}
		
		repositorio.deleteById(id);
	}

	public Map<String, Object> filtrarProjetoPorAreaTematica(String areaTematica, int pag, int tamanho) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map<String, Object> filtrarProjetoPorModalidade(String modalidade, int pag, int tamanho) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> filtrarProjetoPorTitulo(String titulo, int page, int tamanho) {
		// TODO Auto-generated method stub
		return null;
	}

}
