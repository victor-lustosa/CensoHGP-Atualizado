package br.com.unitins.censohgp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import br.com.unitins.censohgp.models.enums.Profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "TB_USER")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserModel implements Serializable {
    public UserModel(@NotBlank @NotNull String registration, @NotBlank String name, @NotBlank String email,
                     @NotBlank String password) {
        super();
        this.registration = registration;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = true;
    }
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long userId;

    @NotNull
    @Column(name = "registration", nullable = false, unique = true)
    private String registration;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "email", nullable = false)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "TB_USER_PROFILE", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "profile")
    private Set<Integer> profiles = new HashSet<>();

    @JsonIgnore
    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "is_active")
    private boolean isActive;

    public void addProfile(Profile profile) {
        profiles.add(profile.getCode());
    }

    public void removeProfile(Profile profile) {
        profiles.remove(profile.getCode());
    }

    public Set<Profile> getProfiles() {
        return profiles.stream().map(Profile::toEnum).collect(Collectors.toSet());
    }
}
