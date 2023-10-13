package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.CursoProjeto;
import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.dto.CursoProjetoDTO;
import com.upe.observatorio.projeto.repositorio.CursoProjetoRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoProjetoServico {

    private final CursoProjetoRepositorio repositorio;
    private final CursoServico cursoServico;
    private final ProjetoServico projetoServico;

    public List<CursoProjeto> listarCursoProjetos() {
        return repositorio.findAll();
    }

    public Optional<CursoProjeto> buscarCursoProjetoPorId(Long id) throws ObservatorioExcecao {
        if (repositorio.findById(id).isEmpty()) {
            throw new ObservatorioExcecao("Não existe um campusCurso associado a este id: " + id);
        }
        return repositorio.findById(id);
    }

    public CursoProjeto adicionarCursoProjeto(CursoProjetoDTO cursoProjeto) throws ObservatorioExcecao {

        Curso cursoExistente = cursoServico.buscarCursoPorId(cursoProjeto.getCursoId());

        Projeto projetoExistente = projetoServico.buscarProjetoPorId(cursoProjeto.getProjetoId());

        Optional<CursoProjeto> cursoProjetoExistente = repositorio.findByCursoAndProjeto(cursoExistente, projetoExistente);
        if (cursoProjetoExistente.isPresent()) {
            throw new ObservatorioExcecao("Já existe um relacionamento criado entre o curso e o projeto informado");
        }

        CursoProjeto cursoProjetoSalvar = new CursoProjeto();
        cursoProjetoSalvar.setCurso(cursoExistente);
        cursoProjetoSalvar.setProjeto(projetoExistente);

        return repositorio.save(cursoProjetoSalvar);
    }

    public void removerCursoProjeto(Long id) throws ObservatorioExcecao {
        if (repositorio.findById(id).isEmpty()) {
            throw new ObservatorioExcecao("Não existe um relacionamento entre curso e projeto associado a este id");
        }

        repositorio.deleteById(id);
    }
}
