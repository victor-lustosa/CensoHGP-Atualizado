package br.com.unitins.censohgp.service;

import br.com.unitins.censohgp.security.ControleAutenticacao;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


public class UsuarioAutenticadoService {

    public static ControleAutenticacao authenticated() {
        try {
            return (ControleAutenticacao) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e) {
            return null;
        }
    }
}
