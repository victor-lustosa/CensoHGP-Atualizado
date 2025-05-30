package br.com.unitins.censohgp.resources;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import br.com.unitins.censohgp.models.enums.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import br.com.unitins.censohgp.repositories.impl.UserRepository;
import br.com.unitins.censohgp.models.dtos.UserDTO;
import br.com.unitins.censohgp.exceptions.BusinessException;
import br.com.unitins.censohgp.models.UserModel;

@RestController
@RequestMapping(value = "/apicensohgp")
@Tag(name = "Usuários", description = "Endpoints para usuários do sistema")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class UserResource {

    private final BCryptPasswordEncoder pe;

    private final UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> findAll() {
        return ResponseEntity.ok(userRepository.findAllByName());
    }

    @GetMapping("/users/registration/{registration}")
    public ResponseEntity<UserModel> findByRegistration(@PathVariable(value = "registration") String registration) {
        return ResponseEntity.ok(userRepository.findByRegistration(registration)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok(userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<UserModel> save(@RequestBody UserDTO userDto) {
        UserModel existingUser = userRepository.findByRegistration(userDto.registration())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        if (existingUser != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esta matrícula já existe no sistema!");
        }
        try {
            UserModel user = new UserModel();
            user.setRegistration(userDto.registration());
            user.setName(userDto.name());
            user.setEmail(userDto.email());
            user.setActive(true);
            user.setPassword(pe.encode(userDto.password()));
            if (userDto.profile().equals("NURSE")) {
                user.addProfile(Profile.NURSE);
            } else if (userDto.profile().equals("ADMIN")) {
                user.addProfile(Profile.ADMIN);
            } else {
                throw new BusinessException("Esse perfil não existe no sistema!");
            }
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/users")
    public ResponseEntity<UserModel> updateUser(@Valid @RequestBody UserDTO userDto) {
        UserModel user = userRepository.findByRegistration(userDto.registration())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        if (user != null && user.getId() != userDto.id()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esta matrícula já existe no sistema!");
        } else if (user != null) {
            try {
                user.setId(userDto.id());
                user.setRegistration(userDto.registration());
                user.setName(userDto.name());
                user.setEmail(userDto.email());
                user.setActive(userDto.isActive());
                if (userDto.password() != null && !userDto.password().isEmpty()) {
                    user.setPassword(pe.encode(userDto.password()));
                }
                if (!user.getProfiles().isEmpty()) {
                    user.removeProfile(Profile.ADMIN);
                    user.removeProfile(Profile.NURSE);
                }
                if (userDto.profile().equals("ADMIN")) {
                    user.addProfile(Profile.ADMIN);
                } else if (userDto.profile().equals("NURSE")) {
                    user.addProfile(Profile.NURSE);
                } else {
                    throw new BusinessException("Esse perfil não existe no sistema!");
                }
                return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário informado não existe!");
        }
    }

    @GetMapping("/users/{profile}/{status}")
    public ResponseEntity<List<UserModel>> getByFilters(@RequestParam(value = "profile") String profile,
                                                        @RequestParam(value = "status") String status) {
        List<UserModel> result = new ArrayList<>();
        if (!profile.isEmpty() && status.isEmpty()) {
            int roleId = Profile.findIdByName(profile);
            result = userRepository.findByProfile(roleId);
        } else if (profile.isEmpty() && !status.isEmpty()) {
            boolean statusBoolean = Boolean.parseBoolean(status);
            result = userRepository.findByActiveStatus(statusBoolean);
        } else if (!profile.isEmpty()) {
            int roleId = Profile.findIdByName(profile);
            boolean statusBoolean = Boolean.parseBoolean(status);
            result = userRepository.findAllFilters(roleId, statusBoolean);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/users/toggle-status/{registration}")
    public ResponseEntity<UserModel> updateStatusUser(@Valid @RequestBody UserDTO userDto,
                                                      @RequestParam(value = "registration") String registration) {
        UserModel user = userRepository.findById(userDto.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        if (registration.equals(user.getRegistration())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode se desativar!");
        } else {
            try {
                user.setActive(!user.isActive());
                return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
            }
        }
    }

}
