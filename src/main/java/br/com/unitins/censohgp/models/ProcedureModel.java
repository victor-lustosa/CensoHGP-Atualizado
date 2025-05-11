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
@Table(name = "TB_PROCEDURE")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProcedureModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long procedureId;

    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    private String description;

    @NotNull
    private boolean active;

    public ProcedureModel(@NotNull @NotBlank String name, String description, @NotNull boolean active) {
        super();
        this.name = name;
        this.description = description;
        this.active = active;
    }

}
