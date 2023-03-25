package com.upe.observatorio.projeto.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.domain.Projeto;
import com.upe.observatorio.projeto.domain.dto.ProjetoDTO;
import com.upe.observatorio.projeto.domain.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.domain.enums.ModalidadeEnum;
import com.upe.observatorio.projeto.repository.ProjetoRepository;
import com.upe.observatorio.usuario.domain.Usuario;
import com.upe.observatorio.usuario.service.UsuarioService;
import com.upe.observatorio.utils.ObservatorioException;

@Service
public class ProjetoService {

	@Autowired
	private ProjetoRepository repositorio;

	@Autowired
	private UsuarioService usuarioService;

	public List<Projeto> listarProjetos() {
		return repositorio.findAll();
	}

	public Optional<Projeto> buscarProjetoPorId(Long id) {
		return repositorio.findById(id);
	}

	public Projeto adicionarProjeto(ProjetoDTO projeto) throws ObservatorioException {
		ModelMapper modelMapper = new ModelMapper();
		Projeto projetoSalvar = modelMapper.map(projeto, Projeto.class);

		if (usuarioService.buscarUsuarioPorId(projeto.getUsuarioId()).isEmpty()) {
			throw new ObservatorioException("Não existe um usuário associado a este id");
		}
		Usuario usuarioExistente = usuarioService.buscarUsuarioPorId(projeto.getUsuarioId()).get();
		projetoSalvar.setUsuario(usuarioExistente);

		return repositorio.save(projetoSalvar);
	}

	public void atualizarProjeto(ProjetoDTO projeto, Long id) throws ObservatorioException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioException("Não existe um projeto associado a este id");
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

	public void removerProjeto(Long id) throws ObservatorioException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioException("Não existe um projeto associado a este id");
		}

		repositorio.deleteById(id);
	}

	public List<Projeto> filtrarProjetoPorTitulo(String titulo) {
		return repositorio.findAllByTituloContainingIgnoreCase(titulo);
	}

	public List<Projeto> filtrarProjetoComTodosFiltros(AreaTematicaEnum areaTematica, ModalidadeEnum modalidade,
			Date dataInicio, Date dataFim) {
		return repositorio.findAllByAreaTematicaAndModalidadeAndDataInicioAndDataFim(areaTematica, modalidade,
				dataInicio, dataFim);
	}

	public int obterQuantidadeTotalDeProjetos() {
		return repositorio.findAll().size();
	}

	public HashMap<String, Integer> obterQuantidadeDeProjetosPorAreaTematica() {
		HashMap<String, Integer> resultado = new HashMap<>();

		int qtdProjetosPesquisa = repositorio.findAllByAreaTematica(AreaTematicaEnum.PESQUISA).size();
		int qtdProjetosExtensao = repositorio.findAllByAreaTematica(AreaTematicaEnum.EXTENSAO).size();
		int qtdProjetosInovacao = repositorio.findAllByAreaTematica(AreaTematicaEnum.INOVACAO).size();

		resultado.put("Pesquisa: ", qtdProjetosPesquisa);
		resultado.put("Extensão: ", qtdProjetosExtensao);
		resultado.put("Inovação: ", qtdProjetosInovacao);

		return resultado;
	}

	public HashMap<String, Integer> obterQuantidadeDeProjetosPorModalidade() {
		HashMap<String, Integer> resultado = new HashMap<>();

		int qtdPrograma = repositorio.findAllByModalidade(ModalidadeEnum.PROGRAMA).size();
		int qtdProjeto = repositorio.findAllByModalidade(ModalidadeEnum.PROJETO).size();
		int qtdCurso = repositorio.findAllByModalidade(ModalidadeEnum.CURSO).size();
		int qtdOficina = repositorio.findAllByModalidade(ModalidadeEnum.OFICINA).size();
		int qtdEvento = repositorio.findAllByModalidade(ModalidadeEnum.EVENTO).size();

		resultado.put("Programa: ", qtdPrograma);
		resultado.put("Projeto", qtdProjeto);
		resultado.put("Curso", qtdCurso);
		resultado.put("Oficina", qtdOficina);
		resultado.put("Evento", qtdEvento);

		return resultado;
	}
}
