package br.com.unitins.censohgp.resources;

import lombok.RequiredArgsConstructor;
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
import br.com.unitins.censohgp.services.EmailService;
import br.com.unitins.censohgp.models.dtos.UserDTO;
import br.com.unitins.censohgp.models.dtos.UserNewPasswordDTO;
import br.com.unitins.censohgp.utils.UserUtil;
import br.com.unitins.censohgp.exceptions.BusinessException;
import br.com.unitins.censohgp.models.UserModel;

@RestController
@RequestMapping(value = "/apicensohgp")
@RequiredArgsConstructor
public class UserResource {

    private final BCryptPasswordEncoder pe;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UserModel>> findAll() {
        return ResponseEntity.ok(userRepository.findAllByName());
    }

    @GetMapping("/usuario/matricula/{matricula}")
    public ResponseEntity<UserModel> findByRegistration(@PathVariable(value = "matricula") String registration) {
        return ResponseEntity.ok(userRepository.findByRegistration(registration)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<UserModel> findById(@PathVariable(value = "idUsuario") long id) {
        return ResponseEntity.ok(userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")));
    }

    @GetMapping("/usuario/perfis")
    public ResponseEntity<List<String>> getProfiles() {
        return ResponseEntity.ok(Profile.getAllProfileNames());
    }

    //  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/usuario")
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

    //  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/usuario")
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

    @GetMapping("/usuario")
    public ResponseEntity<List<UserModel>> getByFilters(@RequestParam(value = "perfil", required = false, defaultValue = "") String role,
                                                        @RequestParam(value = "status", required = false, defaultValue = "") String status) {
        List<UserModel> result = new ArrayList<>();
        if (!role.isEmpty() && status.isEmpty()) {
            int roleId = Profile.findIdByName(role);
            result = userRepository.findByProfile(roleId);
        } else if (role.isEmpty() && !status.isEmpty()) {
            boolean statusBoolean = Boolean.parseBoolean(status);
            result = userRepository.findByActiveStatus(statusBoolean);
        } else if (!role.isEmpty()) {
            int roleId = Profile.findIdByName(role);
            boolean statusBoolean = Boolean.parseBoolean(status);
            result = userRepository.findAllFilters(roleId, statusBoolean);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/usuario/mudar-status")
    public ResponseEntity<UserModel> updateStatusUser(@Valid @RequestBody UserDTO userDto,
                                                      @RequestParam(value = "matricula", required = false, defaultValue = "") String registration) {
        UserModel user = userRepository.findById(userDto.id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
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
    public ResponseEntity<UserModel> requestNewPassword(@RequestBody UserNewPasswordDTO userNewPasswordDTO) {
        UserModel existingUser = userRepository.findByRegistrationAndEmail(userNewPasswordDTO.registration(), userNewPasswordDTO.email());
        if (existingUser != null) {
            try {
                String newPassword = UserUtil.newPassword();
                String message = "<p>Olá," + existingUser.getName() + "!</p><br>" +
                        "<p>Alguém solicitou uma nova senha na sua conta do CensoHGP</p><br>" +
                        "<p>Segue a nova senha: " + newPassword + " </p><br><br>" +
                        "<p>[NÃO RESPONDA - EMAIL GERADO AUTOMATICAMENTE PELO SISTEMA]</p><br><br>" +
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
