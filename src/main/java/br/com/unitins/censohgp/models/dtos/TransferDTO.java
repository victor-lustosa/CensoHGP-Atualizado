package br.com.unitins.censohgp.models.dtos;

public record TransferDTO(
        long patientId,
        String registration,
        String originDepartment,
        long destinationDepartmentId,
        String observation
) {}