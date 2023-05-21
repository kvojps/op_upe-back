package com.upe.observatorio.projeto.dominio;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

	@Column(name = "area_tematica")
	@Enumerated(EnumType.STRING)
	private AreaTematicaEnum areaTematica;

	@Enumerated(EnumType.STRING)
	private ModalidadeEnum modalidade;

	@Column(unique = true)
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

	@Column(name = "memoria_visual")
	private String memoriaVisual;

	@Column(name = "data_inicio")
	@Temporal(TemporalType.DATE)
	private Date dataInicio;

	@Temporal(TemporalType.DATE)
	private Date dataFim;

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
