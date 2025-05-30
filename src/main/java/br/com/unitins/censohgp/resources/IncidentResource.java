package br.com.unitins.censohgp.resources;

import br.com.unitins.censohgp.exceptions.BusinessException;
import br.com.unitins.censohgp.models.IncidentModel;
import br.com.unitins.censohgp.repositories.impl.IncidentRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apicensohgp")
@Tag(name = "Incidentes", description = "Endpoints para incidentes")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class IncidentResource {

    private final IncidentRepository incidentRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/incidents")
    public ResponseEntity<List<IncidentModel>> findAll() {
        return ResponseEntity.ok(incidentRepository.findAllOrderedByName()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incidente não encontrado.")));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/incidents/actives")
    public ResponseEntity<List<IncidentModel>> findAllActive() {
        return ResponseEntity.ok(incidentRepository.findAllActive()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incidente não encontrado.")));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/incidents/inactives")
    public ResponseEntity<List<IncidentModel>> findAllInactive() {
        return ResponseEntity.ok(incidentRepository.findAllInactive()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incidente não encontrado.")));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/incidents/{id}")
    public ResponseEntity<IncidentModel> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(incidentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incidente não encontrado.")));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/incidents")
    public ResponseEntity<IncidentModel> create(@Valid @RequestBody IncidentModel incident) {
        if (incidentRepository.findByName(incident.getName()) != null) {
            throw new BusinessException("Esse incidente já existe no sistema.");
        }

        incident.setActive(true);

        try {
            IncidentModel savedIncident = incidentRepository.save(incident);
            return new ResponseEntity<>(savedIncident, HttpStatus.CREATED);
        } catch (Exception e) {
            String message = Optional.ofNullable(e.getCause()).map(Throwable::getCause).map(Throwable::getMessage).orElse("");
            if (message.contains("duplicate key value violates unique constraint")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Existe outro incidente com o mesmo nome no sistema.");
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema.");
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/incidents")
    public ResponseEntity<IncidentModel> update(@Valid @RequestBody IncidentModel incident) {
        if (incidentRepository.findById(incident.getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incidente não encontrado.");
        }
        try {
            IncidentModel updatedIncident = incidentRepository.save(incident);
            return new ResponseEntity<>(updatedIncident, HttpStatus.CREATED);
        } catch (Exception e) {
            String message = Optional.ofNullable(e.getCause()).map(Throwable::getCause).map(Throwable::getMessage).orElse("");
            if (message.contains("duplicate key value violates unique constraint")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Existe outro incidente com o mesmo nome no sistema.");
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema.");
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/incidents/toggle-status")
    public ResponseEntity<IncidentModel> toggleStatus(@Valid @RequestBody IncidentModel incident) {

        IncidentModel existingIncident = incidentRepository.findById(incident.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incidente não encontrado."));

        existingIncident.setActive(!existingIncident.isActive());
        IncidentModel saved = incidentRepository.save(existingIncident);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
