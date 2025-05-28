package br.com.unitins.censohgp.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import jakarta.persistence.*;
        import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_TRANSFER")
@Getter
@Setter
@NoArgsConstructor
public class TransferModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "transfer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "O Usuário deve ser informado")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @NotNull(message = "O Paciente deve ser informado")
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patient;

    @NotNull(message = "Departamento destino não pode ser nulo")
    @NotBlank(message = "Departamento destino não pode estar vazio")
    @JoinColumn(name = "destination_department")
    private String destinationDepartment;

    @NotNull(message = "Departamento origem não pode ser nulo")
    @NotBlank(message = "Departamento origem não pode estar vazio")
    @JoinColumn(name = "origin_department")
    private String originDepartment;

    private String observation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transfer_date")
    private Date transferDate;

    public String getFormattedTransferDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        LocalDateTime date = transferDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return date.format(formatter);
    }

    @PrePersist
    private void updateDataBeforeInsert() {
        Date date = new Date();
        LocalDateTime localDate = LocalDateTime.now();
        date = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
        this.transferDate = date;
    }

}
