package br.com.unitins.censohgp.resources;

import br.com.unitins.censohgp.models.DepartmentModel;
import br.com.unitins.censohgp.models.dtos.DepartmentDTO;
import br.com.unitins.censohgp.repositories.impl.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/apicensohgp")
@RequiredArgsConstructor
public class DepartmentResource {

    private final DepartmentRepository departmentRepository;

    @GetMapping("/departamentos")
    public ResponseEntity<List<DepartmentModel>> findAll() {
        return ResponseEntity.ok(departmentRepository.findAllByName());
    }

    @GetMapping("/departamento/{idDepartamento}")
    public ResponseEntity<DepartmentModel> findById(@PathVariable("departmentId") long id) {
        return ResponseEntity.ok(departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Departamento não encontrado.")));
    }

    @GetMapping("/departamentos/ativos")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<DepartmentModel>> findAllActive() {
        return ResponseEntity.ok(departmentRepository.findAllActives());
    }

    @GetMapping("/departamento")
    @ResponseStatus(HttpStatus.OK)
    public List<DepartmentModel> getByFilters(
            @RequestParam(value = "tipo", required = false, defaultValue = "") String type,
            @RequestParam(value = "status", required = false, defaultValue = "") String status) {

        if (!type.isEmpty() && status.isEmpty()) {
            boolean typeBoolean = Boolean.parseBoolean(type);
            return departmentRepository.findWithoutStatus(typeBoolean);
        } else if (type.isEmpty() && !status.isEmpty()) {
            boolean statusBoolean = Boolean.parseBoolean(status);
            return departmentRepository.findWithoutProfile(statusBoolean);
        } else if (!type.isEmpty()) {
            boolean typeBoolean = Boolean.parseBoolean(type);
            boolean statusBoolean = Boolean.parseBoolean(status);
            return departmentRepository.findWithAllFilters(typeBoolean, statusBoolean);
        }

        return Collections.emptyList();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/departamento")
    public ResponseEntity<Void> create(@RequestBody @Valid DepartmentDTO dto) {
        if (dto.bedsCount() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number of beds must be greater than zero!");
        }

        if (departmentRepository.findByNameUpperCase(dto.name()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This department already exists in the system!");
        }

        DepartmentModel department = new DepartmentModel();
        department.setName(dto.name());
        department.setBedsCount(dto.bedsCount());
        department.setDescription(dto.description());
        department.setActive(true);
        department.setInternal(dto.isInternal());

        departmentRepository.save(department);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/departamento")
    public ResponseEntity<Void> update(@RequestBody @Valid DepartmentDTO dto) {

        departmentRepository.findByNameUpperCase(dto.name())
                .filter(dep -> dep.getDepartmentId() != dto.departmentId())
                .ifPresent(dep -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This department already exists in the system!");
                });

        DepartmentModel department = departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department not found!"));

        if (dto.bedsCount() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number of beds must be greater than zero!");
        }

        department.setName(dto.name());
        department.setBedsCount(dto.bedsCount());
        department.setDescription(dto.description());
        department.setActive(dto.isActive());
        department.setInternal(dto.isInternal());

        departmentRepository.save(department);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/departamento/mudar-status")
    public ResponseEntity<Void> toggleStatus(@RequestBody @Valid DepartmentDTO dto) {
        DepartmentModel department = departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department not found!"));

        department.setActive(!department.isActive());
        departmentRepository.save(department);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
