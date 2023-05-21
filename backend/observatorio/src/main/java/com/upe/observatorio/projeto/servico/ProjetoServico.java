package com.upe.observatorio.projeto.servico;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.dominio.dto.ProjetoDTO;
import com.upe.observatorio.projeto.dominio.dto.ProjetoFiltroDTO;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import com.upe.observatorio.projeto.repositorio.ProjetoRepositorio;
import com.upe.observatorio.publicacao.dominio.Publicacao;
import com.upe.observatorio.publicacao.dominio.dto.PublicacaoDTO;
import com.upe.observatorio.publicacao.servico.PublicacaoServico;
import com.upe.observatorio.usuario.dominio.Usuario;
import com.upe.observatorio.usuario.servico.UsuarioServico;
import com.upe.observatorio.utils.ObservatorioExcecao;

@Service
public class ProjetoServico {

	@Autowired
	private ProjetoRepositorio repositorio;

	@Autowired
	private UsuarioServico usuarioServico;

	@Autowired
	private CampusServico campusServico;
	
	@Autowired
	private CursoProjetoServico cursoProjetoServico;
	
	@Autowired
	private PublicacaoServico publicacaoServico;

	public List<Projeto> listarProjetos() {
		return repositorio.findAll();
	}
	
	public List<Projeto> listarProjetosPrivados() {
		return repositorio.findProjetosWithPublicacaoNull();
	}
	
	public List<Projeto> listarProjetosPrivadosPorUsuario(Long usuarioId) throws ObservatorioExcecao {
		Optional<Usuario> usuario = usuarioServico.buscarUsuarioPorId(usuarioId);
		
		if (usuario.isEmpty()) {
			throw new ObservatorioExcecao("O usuário não existe!");
		}
		
		return repositorio.findByPublicacaoIsNullAndUsuario(usuario.get());
	}

	public Page<Projeto> listarProjetosPaginado(int page, int size) {
		Pageable requestedPage = PageRequest.of(page, size);
		Page<Projeto> resultado = repositorio.findAll(requestedPage);

		return resultado;
	}

	public Optional<Projeto> buscarProjetoPorId(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um projeto associado a este id!");
		}
		
		return repositorio.findById(id);
	}

	public Projeto adicionarProjeto(ProjetoDTO projeto) throws ObservatorioExcecao {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		Projeto projetoSalvar = modelMapper.map(projeto, Projeto.class);

		if (usuarioServico.buscarUsuarioPorId(projeto.getUsuarioId()).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um usuário associado a este id");
		}
		Usuario usuarioExistente = usuarioServico.buscarUsuarioPorId(projeto.getUsuarioId()).get();
		projetoSalvar.setUsuario(usuarioExistente);

		if (campusServico.buscarCampusPorId(projeto.getCampusId()).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um campus associado a este id" + projeto.getCampusId());
		}	
		Campus campusExistente = campusServico.buscarCampusPorId(projeto.getCampusId()).get();
		projetoSalvar.setCampus(campusExistente);
		
		Projeto projetoSalvo = repositorio.save(projetoSalvar);
		
		if (projeto.isVisibilidade()) {
			PublicacaoDTO publicacaoSalvar = new PublicacaoDTO();
			publicacaoSalvar.setProjeto(projetoSalvar);
			publicacaoSalvar.setUsuario(usuarioExistente);
			
			Publicacao publicacaoSalvo = publicacaoServico.adicionarPublicacao(publicacaoSalvar);
			projetoSalvo.setPublicacao(publicacaoSalvo);
			
			repositorio.save(projetoSalvo);
		}
		
		return projetoSalvo;
	}

	public void atualizarProjeto(ProjetoDTO projeto, Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um projeto associado a este id");
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

	public void removerProjeto(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um projeto associado a este id");
		}

		repositorio.deleteById(id);
	}
	
	public Page<Projeto> filtrarProjetoComTodosFiltros(ProjetoFiltroDTO dto) throws ParseException {

		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date dataInicioParsed = null;	
		Date dataFimParsed = null;
		if (dto.getDataInicio() != null) {
			dataInicioParsed = formato.parse(dto.getDataInicio());			
		}
		if (dto.getDataFim() != null) {
			dataFimParsed = formato.parse(dto.getDataFim());			
		}
		
		Projeto projeto = Projeto.builder().titulo(dto.getTitulo()).areaTematica(dto.getAreaTematica())
				.modalidade(dto.getModalidade()).dataInicio(dataInicioParsed).dataFim(dataFimParsed).build();

		ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();
		Example<Projeto> filtro = Example.of(projeto, matcher);
		Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize());

		return repositorio.findAll(filtro, pageable);

	}
	
	public Page<Projeto> filtrarProjetoPorTitulo(String titulo, int page, int size) {
		Pageable requestedPage = PageRequest.of(page, size);
		Page<Projeto> resultado = repositorio.findAllByTituloContainingIgnoreCase(titulo, requestedPage);

		return resultado;
	}

	public List<Projeto> filtrarProjetosRecentes() {
		return repositorio.findAllOrderByDataFimDesc();
	}
	
	public int obterQuantidadeTotalDeProjetos() {
		return repositorio.findAll().size();
	}

	public HashMap<String, Integer> obterQuantidadeDeProjetosPorAreaTematica() {
		HashMap<String, Integer> resultado = new HashMap<>();

		int qtdProjetosPesquisa = repositorio.findAllByAreaTematica(AreaTematicaEnum.PESQUISA).size();
		int qtdProjetosExtensao = repositorio.findAllByAreaTematica(AreaTematicaEnum.EXTENSAO).size();
		int qtdProjetosInovacao = repositorio.findAllByAreaTematica(AreaTematicaEnum.INOVACAO).size();

		resultado.put("Pesquisa", qtdProjetosPesquisa);
		resultado.put("Extensão", qtdProjetosExtensao);
		resultado.put("Inovação", qtdProjetosInovacao);

		return resultado;
	}

	public HashMap<String, Integer> obterQuantidadeDeProjetosPorModalidade() {
		HashMap<String, Integer> resultado = new HashMap<>();

		int qtdPrograma = repositorio.findAllByModalidade(ModalidadeEnum.PROGRAMA).size();
		int qtdProjeto = repositorio.findAllByModalidade(ModalidadeEnum.PROJETO).size();
		int qtdCurso = repositorio.findAllByModalidade(ModalidadeEnum.CURSO).size();
		int qtdOficina = repositorio.findAllByModalidade(ModalidadeEnum.OFICINA).size();
		int qtdEvento = repositorio.findAllByModalidade(ModalidadeEnum.EVENTO).size();

		resultado.put("Programa", qtdPrograma);
		resultado.put("Projeto", qtdProjeto);
		resultado.put("Curso", qtdCurso);
		resultado.put("Oficina", qtdOficina);
		resultado.put("Evento", qtdEvento);

		return resultado;
	}

	public void carregarProjetosPlanilha(MultipartFile file) throws IOException, ObservatorioExcecao {
		InputStream input = file.getInputStream();
		try (Workbook workbook = new XSSFWorkbook(input)) {
			Sheet sheet = workbook.getSheetAt(0);
			
			for (Row row : sheet) {
				if (row.getRowNum() == 0 || repositorio
						.findByTitulo(row.getCell(2).getStringCellValue()).isPresent()) {
					continue;
				}

				try {
					ProjetoDTO projeto = criarProjetoPorLinha(row);
					Projeto projetoSalvo = adicionarProjeto(projeto);
					long cursoId = (long) (row.getCell(16).getNumericCellValue());
					
					adicionarCursoProjeto(cursoId, projetoSalvo.getId());
				} catch (IllegalStateException | IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private ProjetoDTO criarProjetoPorLinha(Row row) {
		ProjetoDTO projeto = new ProjetoDTO();
		if(row.getCell(0) != null) projeto.setAreaTematica(obterAreaTematica(row.getCell(0).getStringCellValue()));
		if(row.getCell(1) != null) projeto.setModalidade(obterModalidade(row.getCell(1).getStringCellValue()));
		if(row.getCell(2) != null) projeto.setTitulo(row.getCell(2).getStringCellValue());
		if(row.getCell(3) != null) projeto.setResumo(row.getCell(3).getStringCellValue());
		if(row.getCell(4) != null) projeto.setIntroducao(row.getCell(4).getStringCellValue());
		if(row.getCell(5) != null) projeto.setFundamentacao(row.getCell(5).getStringCellValue());
		if(row.getCell(6) != null) projeto.setObjetivos(row.getCell(6).getStringCellValue());
		if(row.getCell(7) != null) projeto.setConclusao(row.getCell(7).getStringCellValue());
		if(row.getCell(8) != null) projeto.setMemoriaVisual(row.getCell(8).getStringCellValue());
		if(row.getCell(9) != null) projeto.setDataInicio(null); ///falta
		if(row.getCell(10) != null) projeto.setDataFim(null); //falta
		if(row.getCell(11) != null) projeto.setPublicoAlvo(row.getCell(11).getStringCellValue());
		if(row.getCell(12) != null) projeto.setPessoasAtendidas((int) row.getCell(12).getNumericCellValue());
		if(row.getCell(13) != null) projeto.setSuporteFinanceiro(row.getCell(13).getStringCellValue());
		if(row.getCell(14) != null) projeto.setUsuarioId(1L);
		if(row.getCell(15) != null) projeto.setCampusId((long) (row.getCell(15).getNumericCellValue()));
		projeto.setVisibilidade(true);
			
		return projeto;
	}
	
	private AreaTematicaEnum obterAreaTematica(String areaTematica) {
		AreaTematicaEnum areaTematicaResponse = AreaTematicaEnum.valueOf(areaTematica);
		
		return areaTematicaResponse;
	}
	
	private ModalidadeEnum obterModalidade(String modalidade) {
		ModalidadeEnum modalidadeResponse = ModalidadeEnum.valueOf(modalidade);
		
		return modalidadeResponse;
	}
	
	private void adicionarCursoProjeto(Long cursoId, Long projetoId) {
		try {
			CursoProjetoDTO cursoProjeto = new CursoProjetoDTO();
			cursoProjeto.setCursoId(cursoId);	
			cursoProjeto.setProjetoId(projetoId);		
			
			cursoProjetoServico.adicionarCursoProjeto(cursoProjeto);
		} catch (ObservatorioExcecao e) {
			e.printStackTrace();
		}
	}
}
