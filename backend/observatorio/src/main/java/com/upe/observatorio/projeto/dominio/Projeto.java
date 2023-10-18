package com.upe.observatorio.projeto.dominio;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import com.upe.observatorio.publicacao.dominio.Publicacao;
import com.upe.observatorio.usuario.dominio.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Projeto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Enumerated(EnumType.STRING)
	private AreaTematicaEnum areaTematica;

	@Enumerated(EnumType.STRING)
	private ModalidadeEnum modalidade;

	@Column(unique = true, columnDefinition = "TEXT")
	private String titulo;

	@Column(columnDefinition = "TEXT")
	private String resumo;

	@Column(columnDefinition = "TEXT")
	private String introducao;

	@Column(columnDefinition = "TEXT")
	private String fundamentacao;

	@Column(columnDefinition = "TEXT")
	private String objetivos;

	@Column(columnDefinition = "TEXT")
	private String conclusao;

	@Column(columnDefinition = "TEXT")
	private String memoriaVisual;

	@Temporal(TemporalType.DATE)
	private Date dataInicio;

	@Temporal(TemporalType.DATE)
	private Date dataFim;

	@Column(columnDefinition = "TEXT")
	private String publicoAlvo;

	private Integer pessoasAtendidas;

	private String suporteFinanceiro;
	
	@OneToOne
	private Publicacao publicacao;

	@OneToMany(mappedBy = "projeto")
	private List<CursoProjeto> cursoProjetos;

	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private Campus campus;
}
