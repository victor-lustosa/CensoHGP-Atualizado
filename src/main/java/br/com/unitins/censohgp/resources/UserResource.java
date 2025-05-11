package br.com.unitins.censohgp.resources;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import br.com.unitins.censohgp.models.enums.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import br.com.unitins.censohgp.repositories.impl.UserRepository;
import br.com.unitins.censohgp.services.EmailService;
import br.com.unitins.censohgp.models.dtos.UserDTO;
import br.com.unitins.censohgp.models.dtos.UserNewPasswordDTO;
import br.com.unitins.censohgp.utils.UserUtil;
import br.com.unitins.censohgp.exceptions.BusinessException;
import br.com.unitins.censohgp.models.User;

@RestController
@RequestMapping(value = "/apicensohgp")
public class UserResource {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/usuarios/health")
    public String health() {
        return "OK";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/usuarios")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/usuario/matricula/{matricula}")
    public User findByRegistration(@PathVariable(value = "matricula") String registration) {
        return userRepository.findByRegistration(registration);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/usuario/{idUsuario}")
    public User findById(@PathVariable(value = "idUsuario") long id) {
        return userRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/usuario/perfis")
    public List<String> getProfiles() {
        return Profile.getAllProfileNames();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/usuario")
    public ResponseEntity<User> save(@RequestBody UserDTO userDto) {
        User existingUser = userRepository.findByRegistration(userDto.registration());
        if (existingUser != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esta matrícula já existe no sistema!");
        }
        try {
            User user = new User();
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
    @PutMapping("/usuario")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserDTO userDto) {
        User user = userRepository.findByRegistration(userDto.registration());
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/usuario")
    public List<User> getByFilters(@RequestParam(value = "perfil", required = false, defaultValue = "") String role,
                                   @RequestParam(value = "status", required = false, defaultValue = "") String status) {
        if (!role.isEmpty() && status.isEmpty()) {
            int roleId = Profile.findIdByName(role);
            return userRepository.findByProfile(roleId);
        } else if (role.isEmpty() && !status.isEmpty()) {
            boolean statusBoolean = Boolean.parseBoolean(status);
            return userRepository.findByActiveStatus(statusBoolean);
        } else if (!role.isEmpty()) {
            int roleId = Profile.findIdByName(role);
            boolean statusBoolean = Boolean.parseBoolean(status);
            return userRepository.findAllFilters(roleId, statusBoolean);
        } else {
            return new ArrayList<>();
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/usuario/mudar-status")
    public ResponseEntity<User> updateStatusUser(@Valid @RequestBody UserDTO userDto,
                                                 @RequestParam(value = "matricula", required = false, defaultValue = "") String registration) {
        User user = userRepository.findById(userDto.id());
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

    @PutMapping("/usuario/solicita-nova-senha")
    public ResponseEntity<User> requestNewPassword(@RequestBody UserNewPasswordDTO userNewPasswordDTO) {
        User existingUser = userRepository.findByRegistrationAndEmail(userNewPasswordDTO.registration(), userNewPasswordDTO.email());
        if (existingUser != null) {
            try {
                String newPassword = UserUtil.newPassword();
                String message = "<p>Olá," + existingUser.getName() + "!</p><br>" +
                        "<p>Alguém solicitou uma nova senha na sua conta do CensoHGP</p><br>"+
                        "<p>Segue a nova senha: " + newPassword + " </p><br><br>"+
                        "<p>[NÃO RESPONDA - EMAIL GERADO AUTOMATICAMENTE PELO SISTEMA]</p><br><br>"+
                        "<p>CENSO HGP - Palmas, Tocantins</p><br>";
                existingUser.setPassword(newPassword);
                userRepository.save(existingUser);
                emailService.sendMailWithInlineResources(existingUser.getEmail(), "CensoHGP - Nova senha", message);
                return new ResponseEntity<>(HttpStatus.CREATED);

            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, tente novamente em alguns instantes!");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Os dados informados não correspondem!");
        }
    }
}
