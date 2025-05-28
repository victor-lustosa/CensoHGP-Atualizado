package br.com.unitins.censohgp.models.dtos;

public record DepartmentDTO(
        long departmentId,
        String name,
        int bedsCount,
        boolean isInternal,
        boolean isActive,
        String description
) {}