package br.com.unitins.censohgp.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
	private long idUsuario;
	private String matricula;
	private String nome;
	private String email;
	private String senha;
	private long perfil;
	private boolean ativo;
	
	public UsuarioDTO(String matricula, String nome, String email, String senha, long perfil, boolean ativo) {
		super();
		this.matricula = matricula;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.perfil = perfil;
		this.ativo = ativo;
	}
	
	
}
