package com.upe.observatorio.projeto.servico;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.dominio.dto.ProjetoDTO;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import com.upe.observatorio.projeto.repositorio.ProjetoRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;

@Service
public class PlanilhaServico {
	
	@Autowired
	private ProjetoRepositorio repositorio;
	
	@Autowired
	private ProjetoServico projetoServico;
	
	@Autowired
	private CursoProjetoServico cursoProjetoServico;
	
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
					Projeto projetoSalvo = projetoServico.adicionarProjeto(projeto);
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
