package br.com.unitins.censohgp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TB_DEPARTAMENTO", uniqueConstraints = {@UniqueConstraint(columnNames={"nome"})})
@Getter
@Setter()
public class Departamento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5027088500729356993L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idDepartamento;
	
	@NotNull
	@NotBlank
	@Column(name="nome", nullable = false)
	private String nome;
	
	@NotNull
	@Column(name="numero_leitos", nullable = false)
	private int numero_leitos;
	

	
	private String descricao;
	
	//@ManyToOne
	//@JoinColumn(name = "id_tipo_departamento", nullable= false)
	//private TipoDepartamento tipodepartamento;
	@NotNull
	private boolean interno;
	
	@NotNull
	private boolean ativo;

	

	public Departamento() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Departamento( @NotNull @NotBlank String nome, @NotNull int numero_leitos,
			String descricao, @NotNull boolean interno, @NotNull boolean ativo) {
		super();
		
		this.nome = nome;
		this.numero_leitos = numero_leitos;
		this.descricao = descricao;
		this.interno = interno;
		this.ativo = ativo;
	}


	
	
	
}
