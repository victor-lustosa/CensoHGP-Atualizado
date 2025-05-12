package br.com.unitins.censohgp.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.unitins.censohgp.exception.NegocioException;
import br.com.unitins.censohgp.model.Incidente;
import br.com.unitins.censohgp.repository.IncidenteRepository;

@RestController
@RequestMapping(value = "/apicensohgp")
public class IncidenteResource {
	
	@Autowired
	IncidenteRepository incidenteRepository;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/incidentes")
	public List<Incidente> findAll(){
		return incidenteRepository.findAll();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/incidente/{idIncidente}")
	public Incidente findById(@PathVariable(value = "idIncidente") long id) {
		return incidenteRepository.findById(id);
	}
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/incidente/nome/{nome}")
	public  List<Incidente>  findBySearch( @PathVariable(value = "nome", required = false) String nome) {
		return incidenteRepository.findBySearch("%"+nome+"%");
	}
	@PostMapping("/incidente")
	public ResponseEntity<Incidente> createIncidente(@Valid @RequestBody Incidente incidente) {
		Incidente incidenteExistente = incidenteRepository.findByNome(incidente.getNome());
		if (incidenteExistente != null) {
			throw new NegocioException("Este incidente já existe no sistema!");
		}
		Incidente incidenteStatus = new Incidente();
		incidenteStatus = incidente;
		incidenteStatus.setAtivo(true);
		return new ResponseEntity<Incidente>(incidenteRepository.save(incidenteStatus), HttpStatus.CREATED);
	}
	
	@PutMapping("/incidente")
	public ResponseEntity<Incidente> updateIncidente(@Valid @RequestBody Incidente incidente){
		Incidente incidenteNaoExiste = incidenteRepository.findById(incidente.getIdIncidente());
		if(incidenteNaoExiste != null) {
			try {
				return new ResponseEntity<Incidente>(incidenteRepository.save(incidente), HttpStatus.CREATED);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
			}
			
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incidente informado não existe!");
		}
	}
	
	@PutMapping("/incidente/mudar-status")
	public ResponseEntity<Incidente> updateStatusIncidente(@Valid @RequestBody Incidente incidente){
		Incidente incidenteDisable = incidenteRepository.findById(incidente.getIdIncidente());

		if(incidenteDisable != null) {
			try {
				if(incidenteDisable.isAtivo()) {
					incidenteDisable.setAtivo(false);
				}else {
					incidenteDisable.setAtivo(true);
				}
				return new ResponseEntity<Incidente>(incidenteRepository.save(incidenteDisable), HttpStatus.CREATED);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
			}
			
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incidente informado não existe!");
		}
	}
}
