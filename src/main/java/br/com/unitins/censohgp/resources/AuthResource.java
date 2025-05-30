package br.com.unitins.censohgp.resources;

import br.com.unitins.censohgp.models.dtos.AuthDTO;
import br.com.unitins.censohgp.models.dtos.TokenDTO;
import br.com.unitins.censohgp.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/apicensohgp")
@RequiredArgsConstructor
public class AuthResource {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthDTO request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.registration(), request.password(), new ArrayList<>()));
        String token = jwtUtil.generateToken(authentication.getName(), authentication.getAuthorities().toString());

        return ResponseEntity.ok(new TokenDTO(token));
    }
}

