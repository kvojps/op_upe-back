package com.upe.observatorio.projeto.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.domain.Projeto;
import com.upe.observatorio.projeto.domain.dto.ProjetoDTO;
import com.upe.observatorio.projeto.repository.ProjetoRepository;
import com.upe.observatorio.projeto.utilities.ProjetoException;

@Service
public class ProjetoServiceImpl implements ProjetoService {

	@Autowired
	private ProjetoRepository repositorio;

	@Override
	public List<Projeto> listarProjetos() {
		return repositorio.findAll();
	}

	@Override
	public Optional<Projeto> buscarProjetoPorId(Long id) {
		return repositorio.findById(id);
	}

	@Override
	public Projeto adicionarProjeto(ProjetoDTO projeto) throws ProjetoException {
		Projeto projetoSalvar = new Projeto();
		BeanUtils.copyProperties(projeto, projetoSalvar);

		return repositorio.save(projetoSalvar);
	}

	@Override
	public void atualizarProjeto(ProjetoDTO projeto, Long id) throws ProjetoException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ProjetoException("Não existe um projeto associado a este id");
		}
		
		Projeto projetoAtualizar = new Projeto();
		BeanUtils.copyProperties(projeto, projetoAtualizar);
		
		repositorio.save(projetoAtualizar);

	}

	@Override
	public void removerProjeto(Long id) throws ProjetoException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ProjetoException("Não existe um projeto associado a este id");
		}
		
		repositorio.deleteById(id);

	}

	@Override
	public Map<String, Object> filtrarProjetoPorAreaTematica(String areaTematica, int pag, int tamanho) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> filtrarProjetoPorModalidade(String modalidade, int pag, int tamanho) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> filtrarProjetoPorTitulo(String titulo, int page, int tamanho) {
		// TODO Auto-generated method stub
		return null;
	}

}
