package com.upe.observatorio.projeto.servico;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.Projeto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjetoServico {

    private final ProjetoRepositorio repositorio;
    private final UsuarioServico usuarioServico;
    private final CampusServico campusServico;
    private final PublicacaoServico publicacaoServico;

    public Projeto adicionarProjeto(ProjetoDTO projeto) throws ObservatorioExcecao {
        Projeto projetoSalvar = new Projeto();
        BeanUtils.copyProperties(projeto, projetoSalvar);

        Usuario usuarioExistente = usuarioServico.buscarUsuarioPorId(projeto.getUsuarioId());
        Campus campusExistente = campusServico.buscarCampusPorId(projeto.getCampusId());
        projetoSalvar.setUsuario(usuarioExistente);
        projetoSalvar.setCampus(campusExistente);

        Projeto projetoSalvo = repositorio.save(projetoSalvar);
        if (projeto.isVisibilidade()) {
            projetoSalvo = adicionarPublicacao(projetoSalvo, usuarioExistente);
        }

        return projetoSalvo;
    }

    private Projeto adicionarPublicacao(Projeto projeto, Usuario usuario) throws ObservatorioExcecao{
        PublicacaoDTO publicacaoSalvar = new PublicacaoDTO();
        publicacaoSalvar.setProjeto(projeto);
        publicacaoSalvar.setUsuario(usuario);

        Publicacao publicacaoSalvo = publicacaoServico.adicionarPublicacao(publicacaoSalvar);
        projeto.setPublicacao(publicacaoSalvo);

        return repositorio.save(projeto);
    }

    public Page<Projeto> listarProjetos(ProjetoFiltroDTO dto, int page, int size) {
        Pageable requestedPage = PageRequest.of(page, size);
        return repositorio.findWithFilters(dto.getDataInicio(), dto.getDataFim(),
                dto.getTitulo(), dto.getAreaTematica(), dto.getModalidade(), requestedPage);
    }

    public Page<Projeto> listarProjetosPrivados(Long usuarioId, int page, int size) throws ObservatorioExcecao {
        Pageable requestedPage = PageRequest.of(page, size);

        if (usuarioId == null) {
            return repositorio.findProjetosWithPublicacaoNullAndUsuario(requestedPage, null);
        }

        Usuario usuario = usuarioServico.buscarUsuarioPorId(usuarioId);
        return repositorio.findProjetosWithPublicacaoNullAndUsuario(requestedPage, usuario);
    }

    public Projeto buscarProjetoPorId(@NotNull Long id) throws ObservatorioExcecao {
        return repositorio.findById(id).orElseThrow(() ->
                new ObservatorioExcecao("Não existe um projeto associado a este id!"));
    }

    public void atualizarProjeto(ProjetoDTO projeto, @NotNull Long id) throws ObservatorioExcecao {
        if (repositorio.findById(id).isEmpty()) {
            throw new ObservatorioExcecao("Não existe um projeto associado a este id");
        }

        Projeto projetoExistente = repositorio.findById(id).get();
        BeanUtils.copyProperties(projeto, projetoExistente);

        repositorio.save(projetoExistente);
    }

    public void removerProjeto(@NotNull Long id) throws ObservatorioExcecao {
        if (repositorio.findById(id).isEmpty()) {
            throw new ObservatorioExcecao("Não existe um projeto associado a este id!");
        }

        repositorio.deleteById(id);
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
}
