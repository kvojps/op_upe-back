package com.upe.observatorio.publicacao.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.servico.ProjetoServico;
import com.upe.observatorio.publicacao.dominio.Publicacao;
import com.upe.observatorio.publicacao.dominio.dto.PublicacaoDTO;
import com.upe.observatorio.publicacao.repositorio.PublicacaoRepositorio;
import com.upe.observatorio.usuario.dominio.Usuario;
import com.upe.observatorio.usuario.servico.UsuarioServico;
import com.upe.observatorio.utils.ObservatorioExcecao;

@Service
public class PublicacaoServico {
	
	@Autowired
	private PublicacaoRepositorio repositorio;
	
	@Autowired
	private ProjetoServico projetoServico;
	
	@Autowired
	private UsuarioServico usuarioServico;
	
	public List<Publicacao> listarPublicacoes() {
		return repositorio.findAll();
	}
	
	public Optional<Publicacao> buscarPublicacaoPorId(Long id) {
		return repositorio.findById(id);
	}
	
	public Publicacao adicionarPublicacao(PublicacaoDTO publicacao) throws ObservatorioExcecao {
		if (projetoServico.buscarProjetoPorId(publicacao.getProjetoId()).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um projeto associado a este id");
		}
		
		if (usuarioServico.buscarUsuarioPorId(publicacao.getUsuarioId()).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um usuário associado a este id");
		}
		
		Projeto projetoSalvar = projetoServico.buscarProjetoPorId(publicacao.getProjetoId()).get();
		Usuario usuarioExistente = usuarioServico.buscarUsuarioPorId(publicacao.getUsuarioId()).get();
		
		Publicacao publicacaoSalvar = new Publicacao();
		publicacaoSalvar.setCurtidas(0);
		publicacaoSalvar.setDescurtidas(0);
		publicacaoSalvar.setVisualizacoes(0);
		publicacaoSalvar.setProjeto(projetoSalvar);
		publicacaoSalvar.setUsuario(usuarioExistente);
		
		return repositorio.save(publicacaoSalvar);
	}
	
	public void removerPublicacao(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um projeto associado a este id");
		}
		
		repositorio.deleteById(id);
	}
}
