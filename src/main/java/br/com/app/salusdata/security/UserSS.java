package br.com.app.salusdata.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.app.salusdata.models.enums.Profile;

@SuppressWarnings("unused")
public class UserSS implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    final private Integer id;

    final private String registration;

    final private String password;

    final private boolean active;

    final private Collection<? extends GrantedAuthority> authorities;

    public UserSS(Integer id, String registration, String password, Set<Profile> profiles, boolean active) {
        super();
        this.id = id;
        this.registration = registration;
        this.password = password;
        this.active = active;
        this.authorities = profiles.stream().map(profile -> new SimpleGrantedAuthority(profile.getDescription())).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return registration;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public boolean hasRole(Profile profile) {
        return getAuthorities().contains(new SimpleGrantedAuthority(profile.getDescription()));
    }
}
