package br.com.unitins.censohgp.dto;

import lombok.Data;

@Data
public class ChecklistDTO {
	private long idDepartamento;
	private String nome;
	private int numero_leitos;
	private long tipodepartamento;
	private boolean ativo;
	
	
	public ChecklistDTO(String nome, int numero_leitos, long tipodepartamento, boolean ativo) {
		super();
		this.nome = nome;
		this.numero_leitos = numero_leitos;
		this.tipodepartamento = tipodepartamento;
		this.ativo = ativo;
	}


}
