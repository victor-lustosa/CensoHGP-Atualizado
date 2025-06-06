package br.com.app.salusdata.models;

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
@Table(name = "TB_PRECAUTION")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PrecautionModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "precaution_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    private String description;

    @NotNull
    @Column(name = "is_active")
    private boolean isActive;

    public PrecautionModel(@NotNull @NotBlank String name, String description, @NotNull boolean isActive) {
        super();
        this.name = name;
        this.description = description;
        this.isActive = isActive;
    }

}
