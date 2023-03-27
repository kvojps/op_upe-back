package com.upe.observatorio.publicacao.dominio;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.usuario.dominio.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Publicacao {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Schema(example="100", description="Quantidade de curtidas de uma publicação")
	private Integer curtidas;
	
	@Schema(example="1", description="Quantidade de descurtidas de uma publicação")
	private Integer descurtidas;
	
	@Schema(example="1000", description="Quantidade de visualizações de uma publicação")
	private Integer visualizacoes;
	
	@OneToOne
	private Projeto projeto;
	
	@ManyToOne
	private Usuario usuario;

	@OneToMany(mappedBy = "publicacao")
	private List<Comentario> comentarios;

}
