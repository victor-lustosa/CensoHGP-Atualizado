 package br.com.app.salusdata.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.app.salusdata.models.UserModel;
import br.com.app.salusdata.repositories.UserRepository;
import org.springframework.web.server.ResponseStatusException;

 @SuppressWarnings("unused")
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String registration) throws UsernameNotFoundException {
        UserModel user = repo.findByRegistration(registration).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe usuário com essa matricula!"));
        if (user == null) {
            throw new UsernameNotFoundException(registration);
        }
        return new UserSS((int) user.getId(), user.getRegistration(), user.getPassword(), user.getProfiles(),user.isActive());
    }
}