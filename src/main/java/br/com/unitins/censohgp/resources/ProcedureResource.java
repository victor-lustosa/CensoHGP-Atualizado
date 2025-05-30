package br.com.unitins.censohgp.resources;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.unitins.censohgp.exceptions.BusinessException;
import br.com.unitins.censohgp.models.ProcedureModel;
import br.com.unitins.censohgp.repositories.impl.ProcedureRepository;

@RestController
@RequestMapping(value = "/apicensohgp")
@Tag(name = "Procedimentos", description = "Endpoints para procedimentos")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ProcedureResource {

    private final ProcedureRepository procedureRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/procedures")
    public ResponseEntity<List<ProcedureModel>> findAll() {
        return ResponseEntity.ok(procedureRepository.findAllByName());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/procedures/actives")
    public ResponseEntity<List<ProcedureModel>> findAllActive() {
        return ResponseEntity.ok(procedureRepository.findAllActive());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/procedures/inactives")
    public ResponseEntity<List<ProcedureModel>> findAllInactive() {
        return ResponseEntity.ok(procedureRepository.findAllInactive());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/procedures/{id}")
    public ResponseEntity<ProcedureModel> findById(@PathVariable(value = "id") long id) {
        ProcedureModel procedure = procedureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Procedimento não encontrado."));
        return ResponseEntity.ok(procedure);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/procedures")
    public ResponseEntity<ProcedureModel> createProcedure(@Valid @RequestBody ProcedureModel procedure) {
        Optional<ProcedureModel> existingProcedure = procedureRepository.findByName(procedure.getName());
        if (existingProcedure.isPresent()) {
            throw new BusinessException("Este procedimento já existe no sistema!");
        }
        procedure.setActive(true);
        ProcedureModel createdProcedure = procedureRepository.save(procedure);
        return new ResponseEntity<>(createdProcedure, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/procedures")
    public ResponseEntity<ProcedureModel> updateProcedure(@Valid @RequestBody ProcedureModel procedure) {
        if (procedureRepository.existsById(procedure.getId())) {
            try {
                ProcedureModel updatedProcedure = procedureRepository.save(procedure);
                return ResponseEntity.ok(updatedProcedure);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Procedimento informado não existe!");
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/procedures/toggle-status")
    public ResponseEntity<ProcedureModel> updateProcedureStatus(@Valid @RequestBody ProcedureModel procedure) {
        Optional<ProcedureModel> existingProcedure = procedureRepository.findById(procedure.getId());

        if (existingProcedure.isPresent()) {
            ProcedureModel procedureToUpdate = existingProcedure.get();
            try {
                procedureToUpdate.setActive(!procedureToUpdate.isActive());
                ProcedureModel updatedProcedure = procedureRepository.save(procedureToUpdate);
                return ResponseEntity.ok(updatedProcedure);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Procedimento informada não existe!");
        }
    }
}
