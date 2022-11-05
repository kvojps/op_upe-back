package com.upe.observatorio.domain;

import com.upe.observatorio.domain.enums.ModalidadeEnum;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Projeto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
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
	
	@Enumerated(EnumType.STRING)
	private ModalidadeEnum modalidade;
	
	private String memoriaVisual;
	
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	
	private String publicoAlvo;
	
	private int pessoasAtendidas;
	
	// palavras chaves
	
	private Double suporteFinanceiro;
	
	@OneToMany(mappedBy = "projeto")
	private List<CursoProjeto> cursoProjeto;
	
	//coordenadores - entidade NtoN
	//professores - entidade NtoN
	//extensionistas - entidade NtoN
}
