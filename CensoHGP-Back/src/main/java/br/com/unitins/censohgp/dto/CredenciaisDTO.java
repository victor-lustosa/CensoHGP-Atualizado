package br.com.unitins.censohgp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Data
@Getter
@Setter
public class CredenciaisDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String matricula;
    private String senha;

    public CredenciaisDTO() {
    }

    public CredenciaisDTO(String matricula, String senha) {
        this.matricula = matricula;
        this.senha = senha;
    }
}
