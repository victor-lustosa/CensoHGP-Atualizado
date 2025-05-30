package br.com.unitins.censohgp.resources;

import br.com.unitins.censohgp.models.*;
import br.com.unitins.censohgp.models.dtos.ChecklistDTO;
import br.com.unitins.censohgp.exceptions.BusinessException;
import br.com.unitins.censohgp.repositories.impl.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/apicensohgp")
@Tag(name = "Checklist", description = "Endpoints para checklists de pacientes")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ChecklistResource {

    private final ChecklistRepository checklistRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final RiskFactorRepository riskFactorRepository;
    private final ProcedureRepository procedureRepository;
    private final IncidentRepository incidentRepository;

    @GetMapping("/checklists")
    public ResponseEntity<List<ChecklistModel>> findAll() {
        return ResponseEntity.ok(checklistRepository.findAll());
    }

    @GetMapping("/checklist/{idChecklist}")
    public ResponseEntity<ChecklistModel> findById(@PathVariable(value = "idChecklist") Long id) {
        return ResponseEntity.ok(checklistRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Checklist não foi encontrado.")));
    }

    @GetMapping("/checklists/paciente/{idPaciente}")
    public ResponseEntity<List<ChecklistModel>> findByPatient(@PathVariable(value = "idPaciente") Long id) {
        return ResponseEntity.ok(checklistRepository.findByPatientId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado checklist para o paciente.")));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/checklist")
    public ChecklistModel save(@RequestBody ChecklistDTO dto) {
        if (dto.userRegistration() == null) {
            throw new BusinessException("Checklist precisa estar associado a um usuário.");
        }

        ChecklistModel checklist = new ChecklistModel();

        UserModel user = userRepository.findByRegistration(dto.userRegistration())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        PatientModel patient = patientRepository.findById(dto.patientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado."));

        checklist.setUser(user);
        checklist.setPatient(patient);
        checklist.setObservation(dto.observation());

        if (dto.riskFactors() != null) {
            checklist.setRiskFactors(dto.riskFactors().stream().map(id -> riskFactorRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fator de Risco não encontrado."))).toList());
        }

        if (dto.incidents() != null) {
            checklist.setIncidents(dto.incidents().stream().map(id -> incidentRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidente não encontrado."))).toList());
        }

        if (dto.procedures() != null) {
            checklist.setProcedures(dto.procedures().stream().map(id -> procedureRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Procedimento não encontrado."))).toList());
        }

        return checklistRepository.save(checklist);
    }
}
