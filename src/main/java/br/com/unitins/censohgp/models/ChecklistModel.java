package br.com.unitins.censohgp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "TB_CHECKLIST")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChecklistModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long checklistId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private PatientModel patient;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_checklist_risk_factor",
            joinColumns = @JoinColumn(name = "checklist_id", referencedColumnName = "checklistId"),
            inverseJoinColumns = @JoinColumn(name = "risk_factor_id", referencedColumnName = "riskFactorId")
    )
    private List<RiskFactorModel> riskFactors;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_checklist_incident",
            joinColumns = @JoinColumn(name = "checklist_id", referencedColumnName = "checklistId"),
            inverseJoinColumns = @JoinColumn(name = "incident_id", referencedColumnName = "incidentId")
    )
    private List<IncidentModel> incidents;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_checklist_procedure",
            joinColumns = @JoinColumn(name = "checklist_id", referencedColumnName = "checklistId"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id", referencedColumnName = "ProcedureId")
    )
    private List<ProcedureModel> procedures;

    private LocalDateTime createdAt;

    private String observation;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return this.createdAt.format(formatter);
    }

}