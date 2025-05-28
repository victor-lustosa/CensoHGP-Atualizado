package br.com.unitins.censohgp.models;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_DEPARTMENT")
@Getter
@Setter
@NoArgsConstructor
public class DepartmentModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "beds_count", nullable = false)
    private int bedsCount;

    private String description;

    @NotNull
    @Column(name = "is_internal")
    private boolean isInternal;

    @NotNull
    @Column(name = "is_active")
    private boolean isActive;

    public DepartmentModel(@NotNull @NotBlank String name, @NotNull int bedsCount,
                           String description, @NotNull boolean isInternal, @NotNull boolean isActive) {
        super();
        this.name = name;
        this.bedsCount = bedsCount;
        this.description = description;
        this.isInternal = isInternal;
        this.isActive = isActive;
    }
}
