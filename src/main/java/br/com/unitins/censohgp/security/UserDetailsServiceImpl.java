package br.com.unitins.censohgp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.unitins.censohgp.models.User;
import br.com.unitins.censohgp.repositories.impl.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String registration) throws UsernameNotFoundException {
        User usu = repo.findByRegistration(registration);
        if (usu == null) {
            throw new UsernameNotFoundException(registration);
        }
        return new UserSS((int) usu.getId(), usu.getRegistration(), usu.getPassword(), usu.getProfiles(),usu.isActive());
    }
}