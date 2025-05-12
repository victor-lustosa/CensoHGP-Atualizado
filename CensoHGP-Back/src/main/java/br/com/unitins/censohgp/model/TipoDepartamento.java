package br.com.unitins.censohgp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="TB_TIPODEPARTAMENTO", uniqueConstraints = {@UniqueConstraint(columnNames={"nome"})})
@Data
public class TipoDepartamento implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 8762039234818349232L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTipoDepartamento;
	
	@NotBlank
	@Column(name="nome", nullable = false)
	private String nome;
	
	private String descricao;

	@NotNull 
	private boolean ativo;
	
	public TipoDepartamento(@NotBlank String nome, String descricao, @NotNull boolean ativo) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
	}

	public TipoDepartamento() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
