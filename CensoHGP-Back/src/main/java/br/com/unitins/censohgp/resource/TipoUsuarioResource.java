package br.com.unitins.censohgp.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import br.com.unitins.censohgp.repository.TipoUsuarioRepository;

import br.com.unitins.censohgp.model.TipoUsuario;

@RestController
@RequestMapping(value = "/apicensohgp")
public class TipoUsuarioResource {
	
	@Autowired
	TipoUsuarioRepository tipoUsuarioRepository;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/tipos-usuarios")
	public List<TipoUsuario> findAll(){
		return tipoUsuarioRepository.findAll();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/tipos-usuario/{idTipoUsuario}")
	public TipoUsuario findById(@PathVariable(value = "idTipoUsuario") long id) {
		return tipoUsuarioRepository.findById(id);
	}
	
}
