package br.com.unitins.censohgp.resources;

import br.com.unitins.censohgp.exceptions.BusinessException;
import br.com.unitins.censohgp.models.RiskFactorModel;
import br.com.unitins.censohgp.repositories.impl.RiskFactorRepository;
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
@RequiredArgsConstructor
public class RiskFactorResource {

    private final RiskFactorRepository riskFactorRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fator-riscos")
    public ResponseEntity<List<RiskFactorModel>> findAll() {
        return ResponseEntity.ok(riskFactorRepository.findAllOrderedByName()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Risk factor not found")));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fator-riscos/ativos")
    public ResponseEntity<List<RiskFactorModel>> findAllActive() {
        return ResponseEntity.ok(riskFactorRepository.findAllActive()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Risk factor not found")));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fator-riscos/inativos")
    public ResponseEntity<List<RiskFactorModel>> findAllInactive() {
        return ResponseEntity.ok(riskFactorRepository.findAllInactive()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Risk factor not found")));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fator-risco/{idFatorRisco}")
    public ResponseEntity<RiskFactorModel> findById(@PathVariable(value = "idFatorRisco") long id) {
        return ResponseEntity.ok(riskFactorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Risk factor not found")));
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/fator-risco")
    public ResponseEntity<RiskFactorModel> create(@Valid @RequestBody RiskFactorModel riskFactor) {
        if (riskFactorRepository.findByName(riskFactor.getName()) != null) {
            throw new BusinessException("A risk factor with this name already exists");
        }
        riskFactor.setActive(true);
        return new ResponseEntity<>(riskFactorRepository.save(riskFactor), HttpStatus.CREATED);
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/fator-risco")
    public ResponseEntity<RiskFactorModel> update(@Valid @RequestBody RiskFactorModel riskFactor) {
        if (riskFactorRepository.findById(riskFactor.getRiskFactorId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Risk factor not found");
        }

        try {
            return new ResponseEntity<>(riskFactorRepository.save(riskFactor), HttpStatus.OK);
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getCause() != null &&
                    e.getCause().getCause().getMessage().contains("duplicate key value violates unique constraint")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A risk factor with this name already exists");
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/fator-risco/mudar-status")
    public ResponseEntity<RiskFactorModel> toggleStatus(@Valid @RequestBody RiskFactorModel riskFactor) {
        RiskFactorModel existingRiskFactor = riskFactorRepository.findById(riskFactor.getRiskFactorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Risk factor not found"));

        existingRiskFactor.setActive(!existingRiskFactor.isActive());

        try {
            return new ResponseEntity<>(riskFactorRepository.save(existingRiskFactor), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
