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
import br.com.unitins.censohgp.model.Precaucao;
import br.com.unitins.censohgp.model.Paciente;
import br.com.unitins.censohgp.model.Precaucao;
import br.com.unitins.censohgp.repository.PrecaucaoRepository;

@RestController
@RequestMapping(value = "/apicensohgp")
public class PrecaucaoResource {
	
	@Autowired
	PrecaucaoRepository precaucaoRepository;
	
	//mudado de precauções para precauçãos pra deixar o trabalho do front facinho
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/precaucaos")
	public List<Precaucao> findAll(){
		return precaucaoRepository.findAll();
	}
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/precaucao/nome/{nome}")
	public  List<Precaucao>  findByNome( @PathVariable(value = "nome", required = false) String nome) {
		return precaucaoRepository.findBySearch("%"+nome+"%");
	}
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/precaucao/{idPrecaucao}")
	public Precaucao findById(@PathVariable(value = "idPrecaucao") long id) {
		return precaucaoRepository.findById(id);
	}
	
	@PostMapping("/precaucao")
	public ResponseEntity<Precaucao> createPrecaucao(@Valid @RequestBody Precaucao precaucao) {
		Precaucao precaucaoExistente = precaucaoRepository.findByNome(precaucao.getNome());
		if (precaucaoExistente != null) {
			throw new NegocioException("Esta precaução já existe no sistema!");
		}	
		Precaucao precaucaoStatus = new Precaucao();
		precaucaoStatus = precaucao;
		precaucaoStatus.setAtivo(true);
		return new ResponseEntity<Precaucao>(precaucaoRepository.save(precaucaoStatus), HttpStatus.CREATED);
	}
	
	@PutMapping("/precaucao")
	public ResponseEntity<Precaucao> updatePrecaucao(@Valid @RequestBody Precaucao precaucao){
		Precaucao precaucaoNaoExiste = precaucaoRepository.findById(precaucao.getIdPrecaucao());
		if(precaucaoNaoExiste != null) {
			try {
				return new ResponseEntity<Precaucao>(precaucaoRepository.save(precaucao), HttpStatus.CREATED);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
			}
			
		}else {
			//throw new NegocioException("Usuário não encontrado no sistema!");
			//return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Precaução informada não existe!");
		}
	}
	
	@PutMapping("/precaucao/mudar-status")
	public ResponseEntity<Precaucao> updateStatusPrecaucao(@Valid @RequestBody Precaucao precaucao){
		Precaucao precaucaoDisable = precaucaoRepository.findById(precaucao.getIdPrecaucao());

		if(precaucaoDisable != null) {
			try {
				if(precaucaoDisable.isAtivo()) {
					precaucaoDisable.setAtivo(false);
				}else {
					precaucaoDisable.setAtivo(true);
				}
				return new ResponseEntity<Precaucao>(precaucaoRepository.save(precaucaoDisable), HttpStatus.CREATED);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
			}
			
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Precaução informada não existe!");
		}
	}
	
}
