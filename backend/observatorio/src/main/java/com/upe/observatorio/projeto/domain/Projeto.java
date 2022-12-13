package com.upe.observatorio.projeto.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.upe.observatorio.projeto.domain.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.domain.enums.ModalidadeEnum;

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

	private Double suporteFinanceiro;

	@OneToMany(mappedBy = "projeto")
	private List<CursoProjeto> cursoProjetos;

	// coordenadores - entidade NtoN
	// professores - entidade NtoN
	// extensionistas - entidade NtoN
}
