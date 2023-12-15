package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.dominio.dto.ProjetoDTO;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import com.upe.observatorio.projeto.dominio.envelopes.StatusExecucaoVO;
import com.upe.observatorio.projeto.repository.ProjectRepository;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SheetService {

	private final ProjectRepository repository;
	private final ProjectService projectService;
	private final CourseProjectService courseProjectService;
	
	public List<StatusExecucaoVO> batchCreateProjects(MultipartFile file)  {
		List<StatusExecucaoVO> statusList = new ArrayList<>();

		try (InputStream input = file.getInputStream()) {
			Workbook workbook = new XSSFWorkbook(input);
			Sheet sheet = workbook.getSheetAt(0);
			
			for (Row row : sheet) {
				if (row.getRowNum() == 0 || repository
						.findByTitulo(row.getCell(2).getStringCellValue()).isPresent()) {
					continue;
				}
				
				formatDateCellValue(workbook, row, 9);
				formatDateCellValue(workbook, row, 10);

				try {
					ProjetoDTO project = createProjectByRow(row);
					Projeto projectSaved = projectService.createProject(project);
					long courseId = (long) (row.getCell(16).getNumericCellValue());
					
					addProjectToCourse(courseId, projectSaved.getId());
				} catch (IllegalStateException | IllegalArgumentException | ObservatorioExcecao | ParseException e) {
					statusList.add(new StatusExecucaoVO("Erro (" +
							row.getCell(2).getStringCellValue() + ") : " + e.getMessage(),
							e.getClass().getSimpleName()));
				}
			}
		} catch (IOException e) {
			statusList.add(new StatusExecucaoVO("Erro: " + e.getMessage(), e.getClass().getSimpleName()));
		}

		return statusList;
	}

	private void formatDateCellValue(Workbook workbook, Row row, int cellIndex) {
		DataFormatter dataFormatter = new DataFormatter();
		Cell dateCellValue = row.getCell(cellIndex);
		String dateCellValueFormatted = dataFormatter.formatCellValue(dateCellValue);

		CellStyle cellStyleStrFormat = workbook.createCellStyle();
		cellStyleStrFormat.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("@"));

		dateCellValue.setCellStyle(cellStyleStrFormat);
		dateCellValue.setCellValue(dateCellValueFormatted);
	}

	private ProjetoDTO createProjectByRow(Row row) throws ParseException {
		String dataInicio = row.getCell(9).getStringCellValue();
		String dataFim = row.getCell(10).getStringCellValue();

		ProjetoDTO project = new ProjetoDTO();
		if(row.getCell(0) != null) project.setAreaTematica(getThematicArea(row.getCell(0).getStringCellValue()));
		if(row.getCell(1) != null) project.setModalidade(getModality(row.getCell(1).getStringCellValue()));
		if(row.getCell(2) != null) project.setTitulo(row.getCell(2).getStringCellValue());
		if(row.getCell(3) != null) project.setResumo(row.getCell(3).getStringCellValue());
		if(row.getCell(4) != null) project.setIntroducao(row.getCell(4).getStringCellValue());
		if(row.getCell(5) != null) project.setFundamentacao(row.getCell(5).getStringCellValue());
		if(row.getCell(6) != null) project.setObjetivos(row.getCell(6).getStringCellValue());
		if(row.getCell(7) != null) project.setConclusao(row.getCell(7).getStringCellValue());
		if(row.getCell(8) != null) project.setMemoriaVisual(row.getCell(8).getStringCellValue());
		if(!dataInicio.isBlank()) project.setDataInicio(getDateByString(dataInicio));
		if(!dataFim.isBlank()) project.setDataFim(getDateByString(dataFim));
		if(row.getCell(11) != null) project.setPublicoAlvo(row.getCell(11).getStringCellValue());
		if(row.getCell(12) != null) project.setPessoasAtendidas((int) row.getCell(12).getNumericCellValue());
		if(row.getCell(13) != null) project.setSuporteFinanceiro(row.getCell(13).getStringCellValue());
		if(row.getCell(14) != null) project.setUsuarioId(1L);
		if(row.getCell(15) != null) project.setCampusId((long) (row.getCell(15).getNumericCellValue()));
		project.setVisibilidade(true);

		return project;
	}

	private void addProjectToCourse(Long courseId, Long projectId) throws ObservatorioExcecao{
		CursoProjetoDTO courseProjectDTO = new CursoProjetoDTO();
		courseProjectDTO.setCursoId(courseId);
		courseProjectDTO.setProjetoId(projectId);

		courseProjectService.createCourseProject(courseProjectDTO);
	}
	
	private AreaTematicaEnum getThematicArea(String thematicArea) {
		return AreaTematicaEnum.valueOf(thematicArea);
	}
	
	private ModalidadeEnum getModality(String modality) {
		return ModalidadeEnum.valueOf(modality);
	}
	
	private Date getDateByString(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return format.parse(date);
	}
}
