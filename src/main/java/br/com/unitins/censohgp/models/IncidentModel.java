package br.com.unitins.censohgp.models;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TB_INCIDENT")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class IncidentModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long incidentId;

    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    private String description;

    @NotNull
    private boolean isActive;

    public IncidentModel(@NotNull @NotBlank String name, String description, @NotNull boolean isActive) {
        super();
        this.name = name;
        this.description = description;
        this.isActive = isActive;
    }

}
