package br.com.unitins.censohgp.resources;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/censo-hgp-api")
public class UserResource {

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

}