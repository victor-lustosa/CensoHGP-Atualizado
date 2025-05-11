package br.com.unitins.censohgp.models.dtos;

public record DepartmentDTO(
        String name,
        int bedCount,
        boolean internal,
        boolean active,
        String description
) {}