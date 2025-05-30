package br.com.unitins.censohgp.resources;

import br.com.unitins.censohgp.exceptions.BusinessException;
import br.com.unitins.censohgp.models.PrecautionModel;
import br.com.unitins.censohgp.repositories.PrecautionRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/apicensohgp")
@Tag(name = "Precauções", description = "Endpoints para precauções")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class PrecautionResource {

    private final PrecautionRepository precautionRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/precautions")
    public List<PrecautionModel> getAll() {
        return precautionRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/precautions/actives")
    public List<PrecautionModel> getAllActive() {
        return precautionRepository.findAllActive();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/precautions/inactives")
    public List<PrecautionModel> getAllInactive() {
        return precautionRepository.findAllInactive();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/precautions/{id}")
    public ResponseEntity<PrecautionModel> getById(@PathVariable(value = "id") Long id) {
        return precautionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Precaução não encontrada."));
    }

   @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
   @ResponseStatus(HttpStatus.OK)
    @PostMapping("/precautions")
    public ResponseEntity<PrecautionModel> create(@Valid @RequestBody PrecautionModel precaution) {
        if (precautionRepository.findByName(precaution.getName()).isPresent()) {
            throw new BusinessException("Precaução já existe no sistema.");
        }
        precaution.setActive(true);
        PrecautionModel saved = precautionRepository.save(precaution);
        return ResponseEntity.status(CREATED).body(saved);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/precautions")
    public ResponseEntity<PrecautionModel> update(@Valid @RequestBody PrecautionModel precaution) {
        return precautionRepository.findById(precaution.getId())
                .map(existing -> {
                    precaution.setId(existing.getId()); // Ensure ID is consistent
                    PrecautionModel updated = precautionRepository.save(precaution);
                    return ResponseEntity.status(CREATED).body(updated);
                })
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Precaução não encontrada."));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/precautions/toggle-status")
    public ResponseEntity<PrecautionModel> toggleStatus(@Valid @RequestBody PrecautionModel precaution) {
        return precautionRepository.findById(precaution.getId())
                .map(existing -> {
                    existing.setActive(!existing.isActive());
                    PrecautionModel updated = precautionRepository.save(existing);
                    return ResponseEntity.status(CREATED).body(updated);
                })
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Precaução não encontrada."));
    }
}
