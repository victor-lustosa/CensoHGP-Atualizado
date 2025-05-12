package br.com.unitins.censohgp.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import br.com.unitins.censohgp.repository.TipoDepartamentoRepository;

import br.com.unitins.censohgp.model.TipoDepartamento;

@RestController
@RequestMapping(value = "/apicensohgp")
public class TipoDepartamentoResource {
	
	@Autowired
	TipoDepartamentoRepository tipoDepartamentoRepository;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/tipo-departamentos")
	public List<TipoDepartamento> findAll(){
		return tipoDepartamentoRepository.findAll();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/tipo-departamento/{idTipoDepartamento}")
	public TipoDepartamento findById(@PathVariable(value = "idTipoDepartamento") long id) {
		return tipoDepartamentoRepository.findById(id);
	}
	
}
