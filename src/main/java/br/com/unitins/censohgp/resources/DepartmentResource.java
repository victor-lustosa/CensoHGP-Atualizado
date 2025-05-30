package br.com.unitins.censohgp.resources;

import br.com.unitins.censohgp.models.DepartmentModel;
import br.com.unitins.censohgp.models.dtos.DepartmentDTO;
import br.com.unitins.censohgp.repositories.DepartmentRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Departamentos", description = "Endpoints para departamentos")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class DepartmentResource {

    private final DepartmentRepository departmentRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentModel>> findAll() {
        return ResponseEntity.ok(departmentRepository.findAllByName());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentModel> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Departamento não encontrado.")));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/departments/actives")
    public ResponseEntity<List<DepartmentModel>> findAllActive() {
        return ResponseEntity.ok(departmentRepository.findAllActives());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/departments/{type}/{status}")
    public List<DepartmentModel> getByFilters(
            @RequestParam(value = "type") String type,
            @RequestParam(value = "status") String status) {

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
    @PostMapping("/departments")
    public ResponseEntity<Void> create(@RequestBody @Valid DepartmentDTO dto) {
        if (dto.bedsCount() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Numero de leitos deve ser maior que zero.");
        }

        if (departmentRepository.findByNameUpperCase(dto.name()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esse departamento já existe no sistema.");
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
    @PutMapping("/departments")
    public ResponseEntity<Void> update(@RequestBody @Valid DepartmentDTO dto) {

        departmentRepository.findByNameUpperCase(dto.name())
                .filter(dep -> dep.getId() != dto.departmentId())
                .ifPresent(dep -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esse departamento já existe no sistema.");
                });

        DepartmentModel department = departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não encontrado."));

        if (dto.bedsCount() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Numero de leitos deve ser maior que zero.");
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
    @PutMapping("/departments/toggle-status")
    public ResponseEntity<Void> toggleStatus(@RequestBody @Valid DepartmentDTO dto) {
        DepartmentModel department = departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não encontrado."));

        department.setActive(!department.isActive());
        departmentRepository.save(department);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
