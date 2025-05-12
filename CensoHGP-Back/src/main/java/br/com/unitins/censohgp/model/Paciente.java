package br.com.unitins.censohgp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TB_PACIENTE", uniqueConstraints = {@UniqueConstraint(columnNames={"cpf"})})
@Getter
@Setter()
public class Paciente implements Serializable {

	private static final long serialVersionUID = 4038057425224115166L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPaciente;

	@NotBlank
	@NotNull
	@Column(name="prontuario", nullable = false)
	private String prontuario;

	@Column(name="nome", nullable = true)
	private String nome;

	@Column(name="nomeMae", nullable = true)
	private String nomeMae;
	
	@Column(name="cpf", nullable = true)
	private String cpf;
		
	@Column(nullable = false)
	private boolean sexoMasculino;
	
	@Column(name="dataNascimento", nullable = true)
	private Date dataNascimento;
	
	@OneToMany ()
	@JoinColumn(name = "idPrecaucao")
	@JoinTable(name = "paciente_precaucao", joinColumns = {
			@JoinColumn(name = "idpaciente", referencedColumnName = "idPaciente") }, inverseJoinColumns = {
					@JoinColumn(name = "idprecaucao", referencedColumnName = "idPrecaucao") })
	private List<Precaucao> precaucao;
	
	@NotNull
	@OneToOne()
	@JoinColumn(name = "id_departamento",unique=true)
	private Departamento departamento;
	
	@OneToMany ()
	@JoinColumn(name = "idChecklist", nullable = true)
	@JoinTable(name = "paciente_checklist", joinColumns = {
			@JoinColumn(name = "idpaciente", referencedColumnName = "idPaciente") }, inverseJoinColumns = {
					@JoinColumn(name = "idchecklist", referencedColumnName = "idChecklist") })
	private List<Checklist> checklist;
	
	public Paciente() {
		super();
	}

	public Paciente(long idPaciente, @NotBlank @NotNull String prontuario, String nome, String nomeMae, String cpf,
			boolean sexoMasculino, Date dataNascimento, List<Precaucao> precaucao, @NotNull Departamento departamento,
			List<Checklist> checklist) {
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





}
