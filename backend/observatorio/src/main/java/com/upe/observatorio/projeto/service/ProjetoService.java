package com.upe.observatorio.projeto.service;

import java.util.List;
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
		
		Projeto projetoExistente = repositorio.findById(id).get();
		if (!projetoExistente.getAreaTematica().equals(projeto.getAreaTematica())) {
			projetoExistente.setAreaTematica(projeto.getAreaTematica());
		}
		
		if (!projetoExistente.getModalidade().equals(projeto.getModalidade())) {
			projetoExistente.setModalidade(projeto.getModalidade());
		}
		
		if (!projetoExistente.getTitulo().equals(projeto.getTitulo())) {
			projetoExistente.setTitulo(projeto.getTitulo());
		}
		
		if (!projetoExistente.getResumo().equals(projeto.getResumo())) {
			projetoExistente.setResumo(projeto.getResumo());
		}
		
		if (!projetoExistente.getIntroducao().equals(projeto.getIntroducao())) {
			projetoExistente.setIntroducao(projeto.getIntroducao());
		}
		
		if (!projetoExistente.getFundamentacao().equals(projeto.getFundamentacao())) {
			projetoExistente.setFundamentacao(projeto.getFundamentacao());
		}
		
		if (!projetoExistente.getObjetivos().equals(projeto.getObjetivos())) {
			projetoExistente.setObjetivos(projeto.getObjetivos());
		}
		
		if (!projetoExistente.getConclusao().equals(projeto.getConclusao())) {
			projetoExistente.setConclusao(projeto.getConclusao());
		}
		
		if (!projetoExistente.getMemoriaVisual().equals(projeto.getMemoriaVisual())) {
			projetoExistente.setMemoriaVisual(projeto.getMemoriaVisual());
		}
		
		if (!projetoExistente.getDataInicio().equals(projeto.getDataInicio())) {
			projetoExistente.setDataInicio(projeto.getDataInicio());
		}
		
		if (!projetoExistente.getDataFim().equals(projeto.getDataFim())) {
			projetoExistente.setDataFim(projeto.getDataFim());
		}
		
		if (!projetoExistente.getDataFim().equals(projeto.getDataFim())) {
			projetoExistente.setDataFim(projeto.getDataFim());
		}
		
		if (!projetoExistente.getPublicoAlvo().equals(projeto.getPublicoAlvo())) {
			projetoExistente.setPublicoAlvo(projeto.getPublicoAlvo());
		}
		
		if (!projetoExistente.getPessoasAtendidas().equals(projeto.getPessoasAtendidas())) {
			projetoExistente.setPessoasAtendidas(projeto.getPessoasAtendidas());
		}
		
		if (!projetoExistente.getSuporteFinanceiro().equals(projeto.getSuporteFinanceiro())) {
			projetoExistente.setSuporteFinanceiro(projeto.getSuporteFinanceiro());
		}
		
		repositorio.save(projetoExistente);
	
	}

	public void removerProjeto(Long id) throws ProjetoException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ProjetoException("Não existe um projeto associado a este id");
		}
		
		repositorio.deleteById(id);
	}

//	public List<Projeto> filtrarProjetoPorAreaTematica(String areaTematica) {
//		return repositorio.findAllByAreaTematicaContainingIgnoreCase(areaTematica);
//	}
//		
//	public List<Projeto> filtrarProjetoPorModalidade(String modalidade) {
//		return repositorio.findAllByModalidadeContainingIgnoreCase(modalidade);
//	}

	public List<Projeto> filtrarProjetoPorTitulo(String titulo) {
		return repositorio.findAllByTituloContainingIgnoreCase(titulo);
	}

}
