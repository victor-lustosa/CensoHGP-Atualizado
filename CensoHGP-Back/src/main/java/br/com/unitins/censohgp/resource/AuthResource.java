package br.com.unitins.censohgp.resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.com.unitins.censohgp.dto.EmailDTO;
import br.com.unitins.censohgp.security.ControleAutenticacao;
import br.com.unitins.censohgp.security.JWTUtil;
import br.com.unitins.censohgp.service.AuthService;
import br.com.unitins.censohgp.service.EmailService;
import br.com.unitins.censohgp.service.SmtpEmailService;
import br.com.unitins.censohgp.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
    @PostMapping("/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        ControleAutenticacao user = UsuarioAutenticadoService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

   @PostMapping("/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
        service.sendNewPassword(objDto.getEmail());
        return ResponseEntity.noContent().build();
    }

}
