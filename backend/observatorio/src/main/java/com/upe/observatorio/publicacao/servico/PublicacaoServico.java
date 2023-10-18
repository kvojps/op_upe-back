package com.upe.observatorio.publicacao.servico;

import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.publicacao.dominio.Publicacao;
import com.upe.observatorio.publicacao.dominio.dto.PublicacaoDTO;
import com.upe.observatorio.publicacao.repositorio.PublicacaoRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
@RequiredArgsConstructor
public class PublicacaoServico {

	private final PublicacaoRepositorio repositorio;
	
	public List<Publicacao> listarPublicacoes() {
		return repositorio.findAll();
	}
	
	public List<Publicacao> listarPublicacoesSemelhantes(AreaTematicaEnum areaTematica) {
		return repositorio.findByProjetoAreaTematica(areaTematica);
	}
	
	public Optional<Publicacao> buscarPublicacaoPorId(Long id) throws ObservatorioExcecao {
		Optional<Publicacao> publicacao = repositorio.findById(id);
		
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe uma publicação associada a este id!");
		}

		int visualizacoes = publicacao.orElseThrow().getVisualizacoes();
		publicacao.orElseThrow().setVisualizacoes(visualizacoes + 1);
		repositorio.save(publicacao.get());
		
		return repositorio.findById(id);
	}
	
	public Publicacao adicionarPublicacao(PublicacaoDTO publicacao) throws ObservatorioExcecao {
		
		Publicacao publicacaoSalvar = new Publicacao();
		publicacaoSalvar.setCurtidas(0);
		publicacaoSalvar.setDescurtidas(0);
		publicacaoSalvar.setVisualizacoes(0);
		publicacaoSalvar.setProjeto(publicacao.getProjeto());
		publicacaoSalvar.setUsuario(publicacao.getUsuario());
		
		return repositorio.save(publicacaoSalvar);
	}
	
	public void removerPublicacao(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um projeto associado a este id");
		}
		
		repositorio.deleteById(id);
	}
	
	public void adicionarCurtida(Long id) throws ObservatorioExcecao {
		Publicacao publicacaoToSave = buscarPublicacaoPorId(id).orElseThrow();
		publicacaoToSave.setCurtidas(publicacaoToSave.getCurtidas() + 1);
		
		repositorio.save(publicacaoToSave);
	}
	
	public void adicionarDescurtida(Long id) throws ObservatorioExcecao {
		Publicacao publicacaoToSave = buscarPublicacaoPorId(id).orElseThrow();
		publicacaoToSave.setDescurtidas(publicacaoToSave.getDescurtidas() + 1);
		
		repositorio.save(publicacaoToSave);
	}
	
	public void removerCurtida(Long id) throws ObservatorioExcecao {
		Publicacao publicacaoToSave = buscarPublicacaoPorId(id).orElseThrow();
		publicacaoToSave.setCurtidas(publicacaoToSave.getCurtidas() - 1);
		
		repositorio.save(publicacaoToSave);
	}
	
	public void removerDescurtida(Long id) throws ObservatorioExcecao {
		Publicacao publicacaoToSave = buscarPublicacaoPorId(id).orElseThrow();
		publicacaoToSave.setDescurtidas(publicacaoToSave.getDescurtidas() - 1);
		
		repositorio.save(publicacaoToSave);
	}
}
