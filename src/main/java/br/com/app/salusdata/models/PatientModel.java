package br.com.app.salusdata.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import br.com.app.salusdata.models.enums.Gender;
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
    @Column(name = "patient_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @NotNull
    @Column(name = "medical_record", nullable = false, unique = true)
    private String medicalRecord;

    @Column(name = "name")
    private String name;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "rg")
    private String rg;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "TB_PATIENT_GENDER")
    private Set<Integer> gender = new HashSet<>();

    @Column(name = "birth_date", nullable = true)
    private Date birthDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @JoinTable(name = "TB_PATIENT_PRECAUTION",
            joinColumns = {@JoinColumn(name = "patient_id", referencedColumnName = "patient_id")},
            inverseJoinColumns = {@JoinColumn(name = "precaution_id", referencedColumnName = "precaution_id")})
    private List<PrecautionModel> precautions;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private DepartmentModel department;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
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
                        Date birthDate, List<PrecautionModel> precautions, DepartmentModel department, UserModel user) {
        this.medicalRecord = medicalRecord;
        this.name = name;
        this.motherName = motherName;
        this.cpf = cpf;
        this.rg = rg;
        this.department = department;
        this.birthDate = birthDate;
        this.precautions = precautions;
        this.user = user;
    }

    public PatientModel(String medicalRecord, String name, String motherName, String cpf, String rg,
                        Date birthDate, List<PrecautionModel> precautions, DepartmentModel department, UserModel user, Gender addGender) {
        this.medicalRecord = medicalRecord;
        this.name = name;
        this.motherName = motherName;
        this.cpf = cpf;
        this.rg = rg;
        this.department = department;
        this.birthDate = birthDate;
        this.precautions = precautions;
        this.user = user;
        this.gender.add(addGender.getCode());
    }

}
