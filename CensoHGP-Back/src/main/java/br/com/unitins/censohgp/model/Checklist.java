package br.com.unitins.censohgp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="TB_CHECKLIST",uniqueConstraints = {@UniqueConstraint(columnNames={"idChecklist"})})
@Getter
@Setter()
public class Checklist implements Serializable {

	private static final long serialVersionUID = 1406098838611661052L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idChecklist;
	

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_paciente")
	private Paciente paciente;

	@OneToMany ()
	@JoinColumn(name = "idFatorRisco")
	@JoinTable(name = "checklist_fator_risco", joinColumns = {
			@JoinColumn(name = "idchecklist", referencedColumnName = "idChecklist") }, inverseJoinColumns = {
					@JoinColumn(name = "idfator_risco", referencedColumnName = "idFatorRisco") })
	private List<FatorRisco> fatorRisco;
		
	@OneToMany ()
	@JoinColumn(name = "idIncidente")
	@JoinTable(name = "checklist_incidente", joinColumns = {
			@JoinColumn(name = "idchecklist", referencedColumnName = "idChecklist") }, inverseJoinColumns = {
					@JoinColumn(name = "idincidente", referencedColumnName = "idIncidente") })
	private List<Incidente> incidente;
	
	@OneToMany ()
	@JoinColumn(name = "idProcedimento")
	@JoinTable(name = "checklist_procedimento", joinColumns = {
			@JoinColumn(name = "idchecklist", referencedColumnName = "idChecklist") }, inverseJoinColumns = {
					@JoinColumn(name = "idprocedimento", referencedColumnName = "idProcedimento") })
	private List<Procedimento> procedimento;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAlteracao;
	@PrePersist
	private void atualizarDadosAntesInsert() {
		this.dataCadastro = new Date();
		this.dataAlteracao = this.dataCadastro;
	}	
	@PreUpdate
	private void atualizarDadosAntesUpdate() {
		this.dataAlteracao = new Date();
	}
	
	
	private String Observacao;
	
}
