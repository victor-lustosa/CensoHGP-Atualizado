package br.com.unitins.censohgp.models.dtos;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

public record CredentialsDTO(
        String registration,
        String password,
        Collection<? extends GrantedAuthority> authorities
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public CredentialsDTO(String registration, String password) {
        this(registration, password, null);
    }
}