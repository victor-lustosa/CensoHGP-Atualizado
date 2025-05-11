package br.com.unitins.censohgp.resources;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.unitins.censohgp.models.TransferModel;
import br.com.unitins.censohgp.models.UserModel;
import br.com.unitins.censohgp.models.PatientModel;
import br.com.unitins.censohgp.models.DepartmentModel;
import br.com.unitins.censohgp.models.dtos.TransferDTO;
import br.com.unitins.censohgp.repositories.impl.TransferRepository;
import br.com.unitins.censohgp.repositories.impl.DepartmentRepository;
import br.com.unitins.censohgp.repositories.impl.PatientRepository;
import br.com.unitins.censohgp.repositories.impl.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/apicensohgp")
@RequiredArgsConstructor
@Slf4j
public class TransferResource {

    private final TransferRepository transferRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;

    @GetMapping("/transferencias")
    public ResponseEntity<List<TransferModel>> findAll() {
        return ResponseEntity.ok(transferRepository.findAll());
    }

    @GetMapping("/transferencia/{idTransferencia}")
    public ResponseEntity<TransferModel> findById(@PathVariable(value = "idTransferencia") long id) {
        return transferRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transferência não encontrada"));
    }

    @GetMapping("/transferencias/paciente/{idPaciente}")
    public ResponseEntity<List<TransferModel>> findByPatient(@PathVariable(value = "idPaciente") long patientId) {
        return ResponseEntity.ok(transferRepository.findByPatientId(patientId));
    }

    @PostMapping("/transferencia")
    public ResponseEntity<TransferModel> createTransfer(@Valid @RequestBody TransferDTO transferDTO) {
        log.info("Creating transfer: {}", transferDTO);

        PatientModel patient = patientRepository.findById(transferDTO.patientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paciente informado não existe!"));

        UserModel user = userRepository.findByRegistration(transferDTO.registration())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário informado não existe!"));

        DepartmentModel origin = departmentRepository.findById(patient.getDepartment().getDepartmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento de origem não foi encontrado!"));

        DepartmentModel destination = departmentRepository.findById(transferDTO.destinationDepartmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento de destino não foi encontrado!"));

        TransferModel transfer = new TransferModel();
        transfer.setPatient(patient);
        transfer.setUser(user);
        transfer.setObservation(transferDTO.observation());
        transfer.setOriginDepartment(origin.getName());
        transfer.setDestinationDepartment(destination.getName());
        patient.setDepartment(destination);

        try {
            patientRepository.save(patient);
            TransferModel savedTransfer = transferRepository.save(transfer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTransfer);
        } catch (Exception e) {
            log.error("Error saving transfer", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
        }
    }
}
