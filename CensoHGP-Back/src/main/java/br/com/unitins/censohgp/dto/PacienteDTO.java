package br.com.unitins.censohgp.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Data
public class PacienteDTO {

	private long idPaciente;
	private String prontuario;
	private String nome;
	private String nomeMae;
	private String cpf;
	private boolean sexoMasculino;
	private Date dataNascimento;
	private List<Long> precaucao;
	private long departamento;
	private long checklist;
	
	public PacienteDTO(long idPaciente, String prontuario, String nome, String nomeMae, String cpf,
			boolean sexoMasculino, Date dataNascimento, List<Long> precaucao, long departamento, long checklist) {
		super();
		this.idPaciente = idPaciente;
		this.prontuario = prontuario;
		this.nome = nome;
		this.nomeMae = nomeMae;
		this.cpf = cpf;
		this.sexoMasculino = sexoMasculino;
		this.dataNascimento = dataNascimento;
		this.precaucao = precaucao;
		this.departamento = departamento;
		this.checklist = checklist;
	}

	@Override
	public String toString() {
		return "PacienteDTO [precaucao=" + precaucao + "]";
	}
	
	
	
	

}
