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
import br.com.unitins.censohgp.model.FatorRisco;
import br.com.unitins.censohgp.model.Precaucao;
import br.com.unitins.censohgp.model.Procedimento;
import br.com.unitins.censohgp.repository.ProcedimentoRepository;
@RestController
@RequestMapping(value = "/apicensohgp")
public class ProcedimentoResource {

	@Autowired
	ProcedimentoRepository procedimentoRepository;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/procedimentos")
	public List<Procedimento> findAll(){
		return procedimentoRepository.findAll();
	}
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/procedimento/nome/{nome}")
	public  List<Procedimento>  findBySearch( @PathVariable(value = "nome", required = false) String nome) {
		return procedimentoRepository.findBySearch("%"+nome+"%");
	}
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/procedimento/{idProcedimento}")
	public Procedimento findById(@PathVariable(value = "idProcedimento") long id) {
		return procedimentoRepository.findById(id);
	}
	
	@PostMapping("/procedimento")
	public ResponseEntity<Procedimento> createProcedimento(@Valid @RequestBody Procedimento procedimento) {
		Procedimento procedimentoExistente = procedimentoRepository.findByNome(procedimento.getNome());
		if (procedimentoExistente != null) {
			throw new NegocioException("Este procedimento já existe no sistema!");
		}
		Procedimento procedimentoStatus = new Procedimento();
		procedimentoStatus = procedimento;
		procedimentoStatus.setAtivo(true);
		return new ResponseEntity<Procedimento>(procedimentoRepository.save(procedimentoStatus), HttpStatus.CREATED);
	}
	
	@PutMapping("/procedimento")
	public ResponseEntity<Procedimento> updateProcedimento(@Valid @RequestBody Procedimento procedimento){
		Procedimento procedimentoNaoExiste = procedimentoRepository.findById(procedimento.getIdProcedimento());
		if(procedimentoNaoExiste != null) {
			try {
				return new ResponseEntity<Procedimento>(procedimentoRepository.save(procedimento), HttpStatus.CREATED);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
			}
			
		}else {
			//throw new NegocioException("Usuário não encontrado no sistema!");
			//return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Procedimento informado não existe!");
		}
	}
	
	@PutMapping("/procedimento/mudar-status")
	public ResponseEntity<Procedimento> updateStatusProcedimento(@Valid @RequestBody Procedimento procedimento){
		Procedimento procedimentoDisable = procedimentoRepository.findById(procedimento.getIdProcedimento());

		if(procedimentoDisable != null) {
			try {
				if(procedimentoDisable.isAtivo()) {
					procedimentoDisable.setAtivo(false);
				}else {
					procedimentoDisable.setAtivo(true);
				}
				return new ResponseEntity<Procedimento>(procedimentoRepository.save(procedimentoDisable), HttpStatus.CREATED);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
			}
			
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Procedimento informada não existe!");
		}
	}
}
