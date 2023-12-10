package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.dto.ProjetoDTO;
import com.upe.observatorio.projeto.dominio.dto.ProjetoFiltroDTO;
import com.upe.observatorio.projeto.repositorio.ProjetoRepositorio;
import com.upe.observatorio.usuario.dominio.Usuario;
import com.upe.observatorio.usuario.servico.UsuarioServico;
import com.upe.observatorio.utils.ProjectResourceNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjetoServico {

    private final ProjetoRepositorio repositorio;
    private final UsuarioServico usuarioServico;
    private final CampusServico campusServico;

    public Projeto adicionarProjeto(ProjetoDTO projeto) {
        Projeto projetoSalvar = new Projeto();
        BeanUtils.copyProperties(projeto, projetoSalvar);

        Usuario usuarioExistente = usuarioServico.buscarUsuarioPorId(projeto.getUsuarioId());
        Campus campusExistente = campusServico.buscarCampusPorId(projeto.getCampusId());
        projetoSalvar.setUsuario(usuarioExistente);
        projetoSalvar.setCampus(campusExistente);

        return repositorio.save(projetoSalvar);
    }

    public Page<Projeto> listarProjetos(ProjetoFiltroDTO dto, int page, int size) {
        Pageable requestedPage = PageRequest.of(page, size);
        return repositorio.findWithFilters(dto.getDataInicio(), dto.getDataFim(),
                dto.getTitulo(), dto.getAreaTematica(), dto.getModalidade(), requestedPage);
    }

    public Projeto buscarProjetoPorId(@NotNull Long id) {
        return repositorio.findById(id).orElseThrow(() ->
                new ProjectResourceNotFoundException("Project not found"));
    }

    public void atualizarProjeto(ProjetoDTO projeto, @NotNull Long id) {
        if (repositorio.findById(id).isEmpty()) {
            throw new ProjectResourceNotFoundException("Project not found");
        }

        Projeto projetoExistente = repositorio.findById(id).get();
        BeanUtils.copyProperties(projeto, projetoExistente);

        repositorio.save(projetoExistente);
    }

    public void removerProjeto(@NotNull Long id) {
        if (repositorio.findById(id).isEmpty()) {
            throw new ProjectResourceNotFoundException("Project not found");
        }

        repositorio.deleteById(id);
    }
}
