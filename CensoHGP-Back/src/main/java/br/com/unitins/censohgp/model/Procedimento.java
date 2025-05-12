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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TB_PROCEDIMENTO", uniqueConstraints = {@UniqueConstraint(columnNames={"nome"})})
@Getter
@Setter()
public class Procedimento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -85029883437510967L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idProcedimento;
	
	@NotNull
	@NotBlank
	@Column(name="nome", nullable = false)
	private String nome;
	
	private String descricao;
	
	@NotNull
	private boolean ativo;

	public Procedimento(@NotNull @NotBlank String nome, String descricao, @NotNull boolean ativo) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
		
	}

	public Procedimento() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
