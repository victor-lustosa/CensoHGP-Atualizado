package br.com.unitins.censohgp.service;


import br.com.unitins.censohgp.model.Usuario;
import br.com.unitins.censohgp.repository.UsuarioRepository;
import br.com.unitins.censohgp.security.ControleAutenticacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String matricula) throws UsernameNotFoundException {
        Usuario usuario = repo.findByMatricula(matricula);
        if (usuario == null) {
            throw new UsernameNotFoundException(matricula);
        }
        return new ControleAutenticacao((int) usuario.getIdUsuario(), usuario.getMatricula(), usuario.getSenha(), usuario.getPerfil()) {
        };
    }
}