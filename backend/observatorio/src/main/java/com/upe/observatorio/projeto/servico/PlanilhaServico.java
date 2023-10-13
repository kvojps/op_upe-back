package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.dominio.dto.ProjetoDTO;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import com.upe.observatorio.projeto.repositorio.ProjetoRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PlanilhaServico {

	private final ProjetoRepositorio repositorio;
	private final ProjetoServico projetoServico;
	private final CursoProjetoServico cursoProjetoServico;
	
	public void carregarProjetosPlanilha(MultipartFile file) throws IOException, ObservatorioExcecao {
		InputStream input = file.getInputStream();
		try (Workbook workbook = new XSSFWorkbook(input)) {
			Sheet sheet = workbook.getSheetAt(0);
			
			for (Row row : sheet) {
				if (row.getRowNum() == 0 || repositorio
						.findByTitulo(row.getCell(2).getStringCellValue()).isPresent()) {
					continue;
				}
				
				corrigirTipoColunaData(workbook, row, 9);
				corrigirTipoColunaData(workbook, row, 10);
			    
				try {
					ProjetoDTO projeto = criarProjetoPorLinha(row);
					Projeto projetoSalvo = projetoServico.adicionarProjeto(projeto);
					long cursoId = (long) (row.getCell(16).getNumericCellValue());
					
					adicionarCursoProjeto(cursoId, projetoSalvo.getId());
				} catch (IllegalStateException | IllegalArgumentException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	private void corrigirTipoColunaData(Workbook workbook, Row row, int nRow) {
		DataFormatter dataFormatter = new DataFormatter();
		Cell cell = row.getCell(nRow);
		String cellValue = dataFormatter.formatCellValue(cell);
		CellStyle stringCellStyle = workbook.createCellStyle();

		stringCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("@"));
		cell.setCellStyle(stringCellStyle);
		cell.setCellValue(cellValue);
	}

	private ProjetoDTO criarProjetoPorLinha(Row row) {
		String dataInicio = row.getCell(9).getStringCellValue();
		String dataFim = row.getCell(10).getStringCellValue();

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
		if(!dataInicio.isBlank()) projeto.setDataInicio(converterStringData(dataInicio));
		if(!dataFim.isBlank()) projeto.setDataFim(converterStringData(dataFim));
		if(row.getCell(11) != null) projeto.setPublicoAlvo(row.getCell(11).getStringCellValue());
		if(row.getCell(12) != null) projeto.setPessoasAtendidas((int) row.getCell(12).getNumericCellValue());
		if(row.getCell(13) != null) projeto.setSuporteFinanceiro(row.getCell(13).getStringCellValue());
		if(row.getCell(14) != null) projeto.setUsuarioId(1L);
		if(row.getCell(15) != null) projeto.setCampusId((long) (row.getCell(15).getNumericCellValue()));
		projeto.setVisibilidade(true);

		return projeto;
	}

	private void adicionarCursoProjeto(Long cursoId, Long projetoId) {
		try {
			CursoProjetoDTO cursoProjeto = new CursoProjetoDTO();
			cursoProjeto.setCursoId(cursoId);	
			cursoProjeto.setProjetoId(projetoId);		
			
			cursoProjetoServico.adicionarCursoProjeto(cursoProjeto);
		} catch (ObservatorioExcecao e) {
			System.err.println(e.getMessage());
		}
	}
	
	private AreaTematicaEnum obterAreaTematica(String areaTematica) {
		return AreaTematicaEnum.valueOf(areaTematica);
	}
	
	private ModalidadeEnum obterModalidade(String modalidade) {
		return ModalidadeEnum.valueOf(modalidade);
	}
	
	private Date converterStringData(String data) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        
        try {
            date = format.parse(data);
        } catch (ParseException e) {
			System.err.println(e.getMessage());
        }
        
        return date;
	}
}
