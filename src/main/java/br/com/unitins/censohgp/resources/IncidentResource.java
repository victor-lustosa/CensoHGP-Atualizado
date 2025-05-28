package br.com.unitins.censohgp.resources;


import br.com.unitins.censohgp.exceptions.BusinessException;
import br.com.unitins.censohgp.models.IncidentModel;
import br.com.unitins.censohgp.repositories.impl.IncidentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apicensohgp")
@RequiredArgsConstructor
public class IncidentResource {

    private final IncidentRepository incidentRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/incidentes")
    public ResponseEntity<List<IncidentModel>> findAll() {
        return ResponseEntity.ok(incidentRepository.findAllOrderedByName()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incident not found")));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/incidentes/ativos")
    public ResponseEntity<List<IncidentModel>> findAllActive() {
        return ResponseEntity.ok(incidentRepository.findAllActive()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incident not found")));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/incidentes/inativos")
    public ResponseEntity<List<IncidentModel>> findAllInactive() {
        return ResponseEntity.ok(incidentRepository.findAllInactive()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incident not found")));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/incidente/{idIncidente}")
    public ResponseEntity<IncidentModel> findById(@PathVariable("idIncidente") long id) {
        return ResponseEntity.ok(incidentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incident not found")));
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/incidente")
    public ResponseEntity<IncidentModel> create(@Valid @RequestBody IncidentModel incident) {
        if (incidentRepository.findByName(incident.getName()) != null) {
            throw new BusinessException("This incident already exists in the system!");
        }

        incident.setActive(true);

        try {
            IncidentModel savedIncident = incidentRepository.save(incident);
            return new ResponseEntity<>(savedIncident, HttpStatus.CREATED);
        } catch (Exception e) {
            String message = Optional.ofNullable(e.getCause()).map(Throwable::getCause).map(Throwable::getMessage).orElse("");
            if (message.contains("duplicate key value violates unique constraint")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Another incident with this name already exists!");
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error, please contact the system administrator.");
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/incidente")
    public ResponseEntity<IncidentModel> update(@Valid @RequestBody IncidentModel incident) {
        if (incidentRepository.findById(incident.getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incident not found!");
        }
        try {
            IncidentModel updatedIncident = incidentRepository.save(incident);
            return new ResponseEntity<>(updatedIncident, HttpStatus.CREATED);
        } catch (Exception e) {
            String message = Optional.ofNullable(e.getCause()).map(Throwable::getCause).map(Throwable::getMessage).orElse("");
            if (message.contains("duplicate key value violates unique constraint")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Another incident with this name already exists!");
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error, please contact the system administrator.");
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/incidente/mudar-status")
    public ResponseEntity<IncidentModel> toggleStatus(@Valid @RequestBody IncidentModel incident) {

        IncidentModel existingIncident = incidentRepository.findById(incident.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incident not found!"));

        existingIncident.setActive(!existingIncident.isActive());
        IncidentModel saved = incidentRepository.save(existingIncident);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
