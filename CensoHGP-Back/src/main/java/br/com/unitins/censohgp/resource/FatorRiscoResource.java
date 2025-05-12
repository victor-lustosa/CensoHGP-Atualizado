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
import br.com.unitins.censohgp.model.FatorRisco;
import br.com.unitins.censohgp.repository.FatorRiscoRepository;

@RestController
@RequestMapping(value = "/apicensohgp")
public class FatorRiscoResource {

	@Autowired
	FatorRiscoRepository fatorRiscoRepository;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/fator-riscos")
	public List<FatorRisco> findAll(){
		return fatorRiscoRepository.findAll();
	}
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/fator-risco/nome/{nome}")
	public List<FatorRisco> findBySearch(@PathVariable(value = "nome",required = false) String nome) {
		return fatorRiscoRepository.findBySearch("%"+nome+"%");
	}
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/fator-risco/{idFatorRisco}")
	public FatorRisco findById(@PathVariable(value = "idFatorRisco") long id) {
		return fatorRiscoRepository.findById(id);
	}
	
	@PostMapping("/fator-risco")
	public ResponseEntity<FatorRisco> createFatorRisco(@Valid @RequestBody FatorRisco fatorRisco) {
		FatorRisco fatorRiscoExistente =  fatorRiscoRepository.findByNome(fatorRisco.getNome());
		if (fatorRiscoExistente != null) {
			throw new NegocioException("Este fator de risco já existe no sistema!");
		}
		FatorRisco incidenteStatus = new FatorRisco();
		incidenteStatus = fatorRisco;
		incidenteStatus.setAtivo(true);
		return new ResponseEntity<FatorRisco>(fatorRiscoRepository.save(incidenteStatus), HttpStatus.CREATED);
	}
	
	@PutMapping("/fator-risco")
	public ResponseEntity<FatorRisco> updateFatorRisco(@Valid @RequestBody FatorRisco fatorRisco){
		FatorRisco fatorRiscoNaoExiste = fatorRiscoRepository.findById(fatorRisco.getIdFatorRisco());
		if(fatorRiscoNaoExiste != null) {
			try {
				return new ResponseEntity<FatorRisco>(fatorRiscoRepository.save(fatorRisco), HttpStatus.CREATED);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
			}
			
		}else {
			//throw new NegocioException("Usuário não encontrado no sistema!");
			//return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fator de risco informado não existe!");
		}
	}
	
	@PutMapping("/fator-risco/mudar-status")
		public ResponseEntity<FatorRisco> updateStatusFatorRisco(@Valid @RequestBody FatorRisco fatorRisco){
			FatorRisco fatorRiscoDisable = fatorRiscoRepository.findById(fatorRisco.getIdFatorRisco());

			if(fatorRiscoDisable != null) {
				try {
					if(fatorRiscoDisable.isAtivo()) {
						fatorRiscoDisable.setAtivo(false);
					}else {
						fatorRiscoDisable.setAtivo(true);
					}
					return new ResponseEntity<FatorRisco>(fatorRiscoRepository.save(fatorRiscoDisable), HttpStatus.CREATED);
				} catch (Exception e) {
					throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
				}
				
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fator de risco informado não existe!");
			}
		}
}
