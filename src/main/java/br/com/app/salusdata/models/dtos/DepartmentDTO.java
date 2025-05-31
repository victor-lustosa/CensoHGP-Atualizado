package br.com.app.salusdata.models.dtos;

public record DepartmentDTO(
        long departmentId,
        String name,
        int bedsCount,
        boolean isInternal,
        boolean isActive,
        String description
) {}