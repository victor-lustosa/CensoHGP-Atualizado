package br.com.unitins.censohgp.resources;

import br.com.unitins.censohgp.exceptions.BusinessException;
import br.com.unitins.censohgp.models.RiskFactorModel;
import br.com.unitins.censohgp.repositories.impl.RiskFactorRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/apicensohgp")
@Tag(name = "Fatores de Risco", description = "Endpoints para fatores de risco")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class RiskFactorResource {

    private final RiskFactorRepository riskFactorRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/risk-factors")
    public ResponseEntity<List<RiskFactorModel>> findAll() {
        return ResponseEntity.ok(riskFactorRepository.findAllOrderedByName()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Risk factor not found")));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/risk-factors/actives")
    public ResponseEntity<List<RiskFactorModel>> findAllActive() {
        return ResponseEntity.ok(riskFactorRepository.findAllActive()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Risk factor not found")));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/risk-factors/inactives")
    public ResponseEntity<List<RiskFactorModel>> findAllInactive() {
        return ResponseEntity.ok(riskFactorRepository.findAllInactive()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Risk factor not found")));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/risk-factors/{id}")
    public ResponseEntity<RiskFactorModel> findById(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok(riskFactorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Risk factor not found")));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/risk-factors")
    public ResponseEntity<RiskFactorModel> create(@Valid @RequestBody RiskFactorModel riskFactor) {
        if (riskFactorRepository.findByName(riskFactor.getName()) != null) {
            throw new BusinessException("Fator de risco com esse nome já existe no sistema.");
        }
        riskFactor.setActive(true);
        return new ResponseEntity<>(riskFactorRepository.save(riskFactor), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/risk-factors")
    public ResponseEntity<RiskFactorModel> update(@Valid @RequestBody RiskFactorModel riskFactor) {
        if (riskFactorRepository.findById(riskFactor.getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fator de risco não encontrado");
        }

        try {
            return new ResponseEntity<>(riskFactorRepository.save(riskFactor), HttpStatus.OK);
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getCause() != null &&
                    e.getCause().getCause().getMessage().contains("duplicate key value violates unique constraint")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Um fator de risco com esse nome já existe no sistema.");
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema.");
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/risk-factors/toggle-status")
    public ResponseEntity<RiskFactorModel> toggleStatus(@Valid @RequestBody RiskFactorModel riskFactor) {
        RiskFactorModel existingRiskFactor = riskFactorRepository.findById(riskFactor.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fator de risco não encontrado."));

        existingRiskFactor.setActive(!existingRiskFactor.isActive());

        try {
            return new ResponseEntity<>(riskFactorRepository.save(existingRiskFactor), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema.");
        }
    }
}
