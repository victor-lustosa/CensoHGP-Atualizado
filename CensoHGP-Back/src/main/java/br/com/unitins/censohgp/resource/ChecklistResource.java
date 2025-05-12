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

import br.com.unitins.censohgp.dto.ChecklistDTO;
import br.com.unitins.censohgp.exception.NegocioException;
import br.com.unitins.censohgp.model.Checklist;
import br.com.unitins.censohgp.repository.ChecklistRepository;

@RestController
@RequestMapping(value = "/apicensohgp")
public class ChecklistResource {
	
	@Autowired
	ChecklistRepository checklistRepository;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/checklists")
	public List<Checklist> findAll(){
		return checklistRepository.findAll();
	}}
	
//	@ResponseStatus(HttpStatus.OK)
//	@GetMapping("/checklist/{idChecklist}")
//	public Checklist findById(@PathVariable(value = "idChecklist") long id) {
//		return checklistRepository.findById(id);
//	}
//	
//	@ResponseStatus(HttpStatus.OK)
//	@GetMapping("/checklists/tipo/id/{idTipoChecklist}")
//	public List<Checklist> findByTipo(@PathVariable(value = "idTipoChecklist") long idTipoChecklist) {
//		return checklistRepository.findByTipo(idTipoChecklist);
//	}
	
//	@ResponseStatus(HttpStatus.OK)
//	@GetMapping("/checklists/ativos")
//	public List<Checklist> findAllAtivos() {
//		return checklistRepository.findAllAtivos();
//	}
//	
//	@ResponseStatus(HttpStatus.OK)
//	@GetMapping("/checklists/inativos")
//	public List<Checklist> findAllInativos() {
//		return checklistRepository.findAllInativos();
//	}
	
//	@ResponseStatus(HttpStatus.OK)
//	@GetMapping("/checklist/nome/{nome}")
//	public  List<Checklist>  findBySearch(@PathVariable(value = "nome", required = false) String nome) {
//		return checklistRepository.findBySearch("%"+nome+"%");
//	}
//	
	
	/*
	@PostMapping("/checklist")
	public ResponseEntity<Checklist> createChecklist(@Valid @RequestBody Checklist checklist) {
		Checklist checklistExistente = checklistRepository.findByNome(checklist.getNome());
		if (checklistExistente != null) {
			throw new NegocioException("Este checklist já existe no sistema!");
		}
		return new ResponseEntity<Checklist>(checklistRepository.save(checklist), HttpStatus.CREATED);
	}
	*/
	
//	//Aplicando padrão DTO
//	@PostMapping("/checklist")
//	public ResponseEntity<Checklist> salvar(@RequestBody ChecklistDTO checklistDto) {
//	    if (checklistRepository.findByNome(checklistDto.getNome()) != null) {
//			throw new NegocioException("Este checklist já existe no sistema!");
//		}
////	    TipoChecklist tipoDep = new TipoChecklist();
////	    tipoDep.setIdTipoChecklist(checklistDto.getTipochecklist());
//	    Checklist dep = new Checklist();
////	    dep.setNome(checklistDto.getNome());
////	    dep.setNumero_leitos(checklistDto.getNumero_leitos());
////	    dep.setAtivo(true);
////	    dep.setTipochecklist(tipoDep);
//	 
//	    return new ResponseEntity<Checklist>(checklistRepository.save(dep), HttpStatus.CREATED);
//		}
	
//	//Aplicando padrão DTO
//	@PutMapping("/checklist")
//	public ResponseEntity<Checklist> updateChecklist(@Valid @RequestBody ChecklistDTO checklistDto){
//		Checklist checklistNaoExiste = checklistRepository.findById(1);
//		
//		if(checklistNaoExiste != null) {
//			try {
//				Checklist dep = new Checklist();
////				 tipoDep.setIdTipoChecklist(checklistDto.getTipochecklist());
////				dep.setIdChecklist(checklistDto.getIdChecklist());
////				dep.setNome(checklistDto.getNome());
////				dep.setNumero_leitos(checklistDto.getNumero_leitos());
////				dep.setAtivo(checklistDto.isAtivo());
////				dep.setTipochecklist(tipoDep);
//				return new ResponseEntity<Checklist>(checklistRepository.save(dep), HttpStatus.CREATED);
//			} catch (Exception e) {
//				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
//			}
//			
//		}else {
//			//throw new NegocioException("Usuário não encontrado no sistema!");
//			//return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Checklist não existe!");
//		}
//	}
	
	/*
	@PutMapping("/checklist")
	public ResponseEntity<Checklist> updateChecklist(@Valid @RequestBody Checklist checklist){
		Checklist checklistNaoExiste = checklistRepository.findById(checklist.getIdChecklist());
		if(checklistNaoExiste != null) {
			try {
				return new ResponseEntity<Checklist>(checklistRepository.save(checklist), HttpStatus.CREATED);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
			}
			
		}else {
			//throw new NegocioException("Usuário não encontrado no sistema!");
			//return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Checklist não existe!");
		}
	}*/
	
	
//	@PutMapping("/checklist/mudar-status")
//	public ResponseEntity<Checklist> updateStatusChecklist(@Valid @RequestBody Checklist checklist){
////		Checklist checklistDisable = checklistRepository.findById(1);
////		System.out.println("checklist: " + checklist + " ChecklistDTO: "  + checklist);
////		if(checklist != null) {
////			try {
////				
////				if(checklistDisable.isAtivo()) {
////					checklistDisable.setAtivo(false);
////				}else {
////					checklistDisable.setAtivo(true);
////				}
////				
////				return new ResponseEntity<Checklist>(checklistRepository.save(checklistDisable), HttpStatus.CREATED);
////			} catch (Exception e) {
////				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
////			}
////			
////		}else {
////			//throw new NegocioException("Usuário não encontrado no sistema!");
////			//return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
////			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Checklist não existe!");
////		}
//	}
//}
