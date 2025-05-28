package br.com.unitins.censohgp.resources;

import br.com.unitins.censohgp.models.UserModel;
import br.com.unitins.censohgp.models.dtos.NewPatientDTO;
import br.com.unitins.censohgp.models.dtos.PatientDTO;
import br.com.unitins.censohgp.models.enums.Gender;
import br.com.unitins.censohgp.models.DepartmentModel;
import br.com.unitins.censohgp.models.PatientModel;
import br.com.unitins.censohgp.models.PrecautionModel;
import br.com.unitins.censohgp.repositories.impl.DepartmentRepository;
import br.com.unitins.censohgp.repositories.impl.PatientRepository;
import br.com.unitins.censohgp.repositories.impl.PrecautionRepository;
import br.com.unitins.censohgp.repositories.impl.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/apicensohgp")
@RequiredArgsConstructor
public class PatientResource {

    private final PatientRepository patientRepository;

    private final DepartmentRepository departmentRepository;

    private final PrecautionRepository precautionRepository;

    private final UserRepository userRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pacientes")
    public List<PatientModel> findAll() {
        return patientRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/paciente/{idPaciente}")
    public PatientModel findById(@PathVariable("idPaciente") long id) {
        return patientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pacientes/departamento/{departamento}")
    public List<PatientModel> findByDepartment(@PathVariable("departamento") String name) {
        DepartmentModel department = departmentRepository.findByNameUpperCase(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid department"));
        return patientRepository.findByDepartment(department.getDepartmentId());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/paciente")
    public ResponseEntity<Void> create(@RequestBody @Valid NewPatientDTO dto,
                                       @RequestParam(value = "matriculaUsuario", defaultValue = "") String userRegistration) {

        if (patientRepository.findByMedicalRecord(dto.medicalRecord()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This patient already exists");
        }

        DepartmentModel department = departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department is required"));

        UserModel user = userRepository.findByRegistration(userRegistration)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department is required"));

        List<PrecautionModel> precautions = new ArrayList<>();
        if (dto.precautions() != null) {
            for (Long id : dto.precautions()) {
                precautions.add(precautionRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department is required")));
            }
        }

        PatientModel patient = new PatientModel();
        patient.setName(dto.name());
        patient.setMedicalRecord(dto.medicalRecord());
        patient.setMotherName(dto.motherName());
        patient.setCpf(dto.cpf());
        patient.setRg(dto.rg());
        patient.setUser(user);
        patient.setBirthDate(dto.birthDate());
        patient.setDepartment(department);
        patient.setPrecautions(precautions);

        patient.getGender().clear();
        if ("Masculino".equalsIgnoreCase(dto.gender())) {
            patient.addGender(Gender.MALE);
        } else if ("Feminino".equalsIgnoreCase(dto.gender())) {
            patient.addGender(Gender.FEMALE);
        }

        patientRepository.save(patient);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/paciente")
    public ResponseEntity<Void> updatePatient(@Valid @RequestBody PatientDTO dto,
                                              @RequestParam(value = "matriculaUsuario", defaultValue = "") String userRegistration) {

        PatientModel patient = patientRepository.findById(dto.patientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        DepartmentModel department = departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department is required"));

        UserModel user = userRepository.findByRegistration(userRegistration)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department is required"));

        List<PrecautionModel> precautions = new ArrayList<>();
        if (dto.precautions() != null) {
            for (Long id : dto.precautions()) {
                var precaution = precautionRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department is required"));
                precautions.add(precaution);
            }
        }

        if (dto.name() != null) patient.setName(dto.name());
        if (dto.rg() != null) patient.setRg(dto.rg());
        if (dto.cpf() != null) patient.setCpf(dto.cpf());
        if (dto.motherName() != null) patient.setMotherName(dto.motherName());
        if (dto.birthDate() != null) patient.setBirthDate(dto.birthDate());

        patient.setUser(user);
        patient.setDepartment(department);
        patient.setPrecautions(precautions);

        patient.getGender().clear();
        if ("Masculino".equalsIgnoreCase(dto.gender())) {
            patient.addGender(Gender.MALE);
        } else if ("Feminino".equalsIgnoreCase(dto.gender())) {
            patient.addGender(Gender.FEMALE);
        }

        patientRepository.save(patient);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
