package com.upe.observatorio.projeto.servico;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.CursoProjeto;
import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.repositorio.CursoProjetoRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;

@Service
public class CursoProjetoServico {
	
	@Autowired
	private CursoProjetoRepositorio repositorio;
	
	@Autowired
	private CursoServico cursoServico;
	
	@Autowired
	private ProjetoServico projetoServico;
	
	public List<CursoProjeto> listarCursoProjetos() {
		return repositorio.findAll();
	}

	public Optional<CursoProjeto> buscarCursoProjetoPorId(Long id) {
		return repositorio.findById(id);
	}

	public CursoProjeto adicionarCursoProjeto(CursoProjetoDTO cursoProjeto) throws ObservatorioExcecao {

		Optional<Curso> cursoExistente = cursoServico.buscarCursoPorId(cursoProjeto.getCursoId());
		if (cursoExistente.isEmpty()) {
			throw new ObservatorioExcecao("O curso informado não existe");
		}
		
		Optional<Projeto> projetoExistente = projetoServico.buscarProjetoPorId(cursoProjeto.getProjetoId());
		if (projetoExistente.isEmpty()) {
			throw new ObservatorioExcecao("O projeto informado não existe");
		}
		
		Optional<CursoProjeto> cursoProjetoExistente = repositorio.findByCursoAndProjeto(cursoExistente.get(), projetoExistente.get());
		if (cursoProjetoExistente.isPresent()) {
			throw new ObservatorioExcecao("Já existe um relacionamento criado entre o curso e o projeto informado");
		}
		
		CursoProjeto cursoProjetoSalvar = new CursoProjeto();
		cursoProjetoSalvar.setCurso(cursoExistente.get());
		cursoProjetoSalvar.setProjeto(projetoExistente.get());
		
		return repositorio.save(cursoProjetoSalvar);
	}

	public void removerCursoProjeto(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um relacionamento entre curso e projeto associado a este id");
		}
		
		repositorio.deleteById(id);	
	}
	
	public HashMap<String, Integer> obterQuantidadeDeProjetosPorCurso() {
		HashMap<String, Integer> resultado = new HashMap<>();
		List<CursoProjeto> cursoProjetos = repositorio.findAll();
		
		for (CursoProjeto cursoProjeto : cursoProjetos) {
			Curso curso = cursoServico.buscarCursoPorId(cursoProjeto.getCurso().getId()).get();
			
			if (resultado.containsKey(curso.getNome())) {
				Integer qtdProjetos = resultado.get(curso.getNome());
				resultado.put(curso.getNome(), qtdProjetos + 1);
			} else {
				resultado.put(curso.getNome(), 1);
			}
		}
		
		return resultado;
	}

}
