 package br.com.unitins.censohgp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.unitins.censohgp.models.UserModel;
import br.com.unitins.censohgp.repositories.impl.UserRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String registration) throws UsernameNotFoundException {
        UserModel usu = repo.findByRegistration(registration).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe usuário com essa matricula!"));
        if (usu == null) {
            throw new UsernameNotFoundException(registration);
        }
        return new UserSS((int) usu.getUserId(), usu.getRegistration(), usu.getPassword(), usu.getProfiles(),usu.isActive());
    }
}