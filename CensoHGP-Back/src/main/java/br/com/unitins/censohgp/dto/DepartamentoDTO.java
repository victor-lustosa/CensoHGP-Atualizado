package br.com.unitins.censohgp.dto;

import lombok.Data;

@Data
public class DepartamentoDTO {
	private long idDepartamento;
	private String nome;
	private int numero_leitos;
	private boolean interno;
	private boolean ativo;
	private String descricao;
	
	
	public DepartamentoDTO(String nome, int numero_leitos, boolean interno, boolean ativo,  String descricao) {
		super();
		this.nome = nome;
		this.numero_leitos = numero_leitos;
		this.interno = interno;
		this.ativo = ativo;
		this.descricao = descricao;
	}


}
