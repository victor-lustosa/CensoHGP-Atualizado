package br.com.unitins.censohgp.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import br.com.unitins.censohgp.models.enums.Gender;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "TB_PATIENT")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class PatientModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long patientId;

    @NotBlank
    @NotNull
    @Column(name = "medicalRecord", nullable = false, unique = true)
    private String medicalRecord;

    @Column(name = "name")
    private String name;

    @Column(name = "motherName")
    private String motherName;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "rg")
    private String rg;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "TB_PATIENT_GENDER")
    private Set<Integer> gender = new HashSet<>();

    @Column(name = "birthDate", nullable = true)
    private Date birthDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @JoinTable(name = "tb_Patient_Prevention",
            joinColumns = {@JoinColumn(name = "patient_id", referencedColumnName = "patientId")},
            inverseJoinColumns = {@JoinColumn(name = "prevention_id", referencedColumnName = "preventionId")})
    private List<PreventionModel> prevention;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "departmentId")
    private DepartmentModel department;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private UserModel user;

    public void addGender(Gender addGender) {
        gender.add(addGender.getCode());
    }

    public Set<Gender> getGender() {
        return gender.stream().map(Gender::toEnum).collect(Collectors.toSet());
    }

    public void removeGender(Gender removeGender) {
        gender.remove(removeGender.getCode());
    }

    public PatientModel(String medicalRecord, String name, String motherName, String cpf, String rg,
                        Date birthDate, List<PreventionModel> prevention, DepartmentModel department, UserModel user) {
        this.medicalRecord = medicalRecord;
        this.name = name;
        this.motherName = motherName;
        this.cpf = cpf;
        this.rg = rg;
        this.department = department;
        this.birthDate = birthDate;
        this.prevention = prevention;
        this.user = user;
    }

}
