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
@Table(name="TB_TIPOUSUARIO", uniqueConstraints = {@UniqueConstraint(columnNames={"nome"})})
@Data
public class TipoUsuario implements Serializable{

	private static final long serialVersionUID = -2463804080685393508L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTipoUsuario;
	
	@NotBlank
	@Column(name="nome", nullable = false)
	private String nome;
	
	private String descricao;
	
	@NotNull 
	private boolean ativo;

	public TipoUsuario(@NotBlank String nome, String descricao, @NotNull boolean ativo) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
	}

	public TipoUsuario() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
