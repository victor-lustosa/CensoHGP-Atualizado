package br.com.unitins.censohgp.models.dtos;

public record UserDTO(
        long id,
        String registration,
        String name,
        String email,
        String password,
        String profile,
        boolean isActive
) { }
