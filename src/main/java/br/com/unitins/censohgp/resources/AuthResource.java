package br.com.unitins.censohgp.resources;

import br.com.unitins.censohgp.models.UserModel;
import br.com.unitins.censohgp.models.dtos.AuthDTO;
import br.com.unitins.censohgp.models.dtos.TokenDTO;
import br.com.unitins.censohgp.models.dtos.UserNewPasswordDTO;
import br.com.unitins.censohgp.models.enums.Profile;
import br.com.unitins.censohgp.repositories.impl.UserRepository;
import br.com.unitins.censohgp.services.EmailService;
import br.com.unitins.censohgp.utils.JWTUtil;
import br.com.unitins.censohgp.utils.UserUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/apicensohgp/auth")
@Tag(name = "Autenticação", description = "Endpoints para autenticação de usuários")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AuthResource {

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    private final UserRepository userRepository;

    private final JWTUtil jwtUtil;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthDTO request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.registration(), request.password(), new ArrayList<>()));
        return ResponseEntity.ok(new TokenDTO(jwtUtil.generateToken(authentication.getName(), authentication.getAuthorities().toString())));
    }

    @PutMapping("/users/request-new-password")
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

    @GetMapping("/users/profiles")
    public ResponseEntity<List<String>> getProfiles() {
        return ResponseEntity.ok(Profile.getAllProfileNames());
    }


}

