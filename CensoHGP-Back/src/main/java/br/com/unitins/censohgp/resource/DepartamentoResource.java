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

import br.com.unitins.censohgp.dto.DepartamentoDTO;
import br.com.unitins.censohgp.exception.NegocioException;
import br.com.unitins.censohgp.model.Departamento;
import br.com.unitins.censohgp.model.TipoDepartamento;
import br.com.unitins.censohgp.repository.DepartamentoRepository;

@RestController
@RequestMapping(value = "/apicensohgp")
public class DepartamentoResource {

	@Autowired
	DepartamentoRepository departamentoRepository;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos")
	public List<Departamento> findAll() {
		return departamentoRepository.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamento/{idDepartamento}")
	public Departamento findById(@PathVariable(value = "idDepartamento") long id) {
		return departamentoRepository.findById(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/tipo/{interno}")
	public List<Departamento> findByTipo(@PathVariable(value = "interno") boolean interno) {
		return departamentoRepository.findByTipo(interno);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/ativos")
	public List<Departamento> findAllAtivos() {
		return departamentoRepository.findAllAtivos();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/inativos")
	public List<Departamento> findAllInativos() {
		return departamentoRepository.findAllInativos();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamento/nome/{nome}")
	public List<Departamento> findBySearch(@PathVariable(value = "nome", required = false) String nome) {
		System.out.println("Chamou a busca por nome e veio: "+nome);
		return departamentoRepository.findBySearch("%" + nome + "%");
	}
	
//	METODOS DE FILTROS
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/ativos-internos")//ativos internos
	public List<Departamento> findAllAtivosInternos() {
		return departamentoRepository.findAllAtivosInternos();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/ativos-externos")//ativos externos
	public List<Departamento> findAllAtivosExternos() {
		return departamentoRepository.findAllAtivosExternos();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/inativos-internos")//inativos internos
	public List<Departamento> findAllInativosInternos() {
		return departamentoRepository.findAllInativosInternos();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/inativos-externos")//inativos externos
	public List<Departamento> findAllInativosExternos() {
		return departamentoRepository.findAllInativosExternos();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/externos")//inativos externos
	public List<Departamento> findAllExternos() {
		return departamentoRepository.findAllExternos();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/internos")//inativos externos
	public List<Departamento> findAllInternos() {
		return departamentoRepository.findAllInternos();
	}
	
//	METODOS DE FILTROS COM PESQUISA
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/nome-internos/{nome}")
	public List<Departamento> findByNomeInternos(@PathVariable(value = "nome", required = false) String nome) {
		return departamentoRepository.findByNomeInternos("%" + nome + "%");
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/nome-ativos/{nome}")
	public List<Departamento> findByNomeAtivos(@PathVariable(value = "nome", required = false) String nome) {
		return departamentoRepository.findByNomeAtivos("%" + nome + "%");
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/nome-ativos-internos/{nome}")
	public List<Departamento> findByNomeAtivosInternos(@PathVariable(value = "nome", required = false) String nome) {
		return departamentoRepository.findByNomeAtivosInternos("%" + nome + "%");
	}
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/nome-ativos-externos/{nome}")
	public List<Departamento> findByNomeAtivosExternos(@PathVariable(value = "nome", required = false) String nome) {
		return departamentoRepository.findByNomeAtivosExternos("%" + nome + "%");
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/nome-inativos/{nome}")
	public List<Departamento> findByNomeInativos(@PathVariable(value = "nome", required = false) String nome) {
		return departamentoRepository.findByNomeInativos("%" + nome + "%");
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/nome-inativos-internos/{nome}")
	public List<Departamento> findByNomeInativosInternos(@PathVariable(value = "nome", required = false) String nome) {
		return departamentoRepository.findByNomeInativosInternos("%" + nome + "%");
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/nome-inativos-externos/{nome}")
	public List<Departamento> findByNomeInativosExternos(@PathVariable(value = "nome", required = false) String nome) {
		return departamentoRepository.findByNomeInativosExternos("%" + nome + "%");
	}
	// Aplicando padrão DTO
	@PostMapping("/departamento")
	public ResponseEntity<String> salvar(@RequestBody @Valid DepartamentoDTO departamentoDto) {
		if (departamentoRepository.findByNomeUpperCase(departamentoDto.getNome()) != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este departamento já existe no sistema!");
			// return ResponseEntity.noContent().build();
		}
		if (departamentoDto.getNumero_leitos() < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número de leitos deve ser maior que zero!");
		}
		Departamento dep = new Departamento();
		dep.setNome(departamentoDto.getNome());
		dep.setNumero_leitos(departamentoDto.getNumero_leitos());
		dep.setDescricao(departamentoDto.getDescricao());
		dep.setAtivo(true);
		dep.setInterno(departamentoDto.isInterno());
		try {
			departamentoRepository.save(dep);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este departamento já existe no sistema!");
		}

		// return ResponseEntity<Departamento>(departamentoRepository.save(dep),
		// HttpStatus.CREATED);
	}

	// Aplicando padrão DTO
	@PutMapping("/departamento")
	public ResponseEntity<String> updateDepartamento(@Valid @RequestBody DepartamentoDTO departamentoDto) {
		Departamento departamentoExiste = departamentoRepository.findById(departamentoDto.getIdDepartamento());
		Departamento departamentoMesmoNome = departamentoRepository.findByNomeUpperCase(departamentoDto.getNome());
		// pega o nome e o id que o usuario enviou
		// se existir o nome informado com id diferente signfica que já existe
		// departamento cadastrado com o nome
		if (departamentoMesmoNome != null && departamentoMesmoNome.getIdDepartamento() != departamentoDto.getIdDepartamento()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este departamento já existe no sistema!");
			// return ResponseEntity.noContent().build();
		}
		if (departamentoDto.getNumero_leitos() < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número de leitos deve ser maior que zero!");
		}
		if (departamentoExiste != null) {
			try {
				Departamento dep = new Departamento();
				dep.setIdDepartamento(departamentoDto.getIdDepartamento());
				dep.setNome(departamentoDto.getNome());
				dep.setNumero_leitos(departamentoDto.getNumero_leitos());
				dep.setDescricao(departamentoDto.getDescricao());
				dep.setAtivo(departamentoDto.isAtivo());
				dep.setInterno(departamentoDto.isInterno());
				departamentoRepository.save(dep);
				return new ResponseEntity<>(HttpStatus.CREATED);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Houve algum problema durante o processamento, tente novamente ou contacte o administrador do sistema!");
			}

		} else {
			// throw new NegocioException("Usuário não encontrado no sistema!");
			// return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível encontrar o departamento informado!");
		}
	}

	/*
	 * @PutMapping("/departamento") public ResponseEntity<Departamento>
	 * updateDepartamento(@Valid @RequestBody Departamento departamento){
	 * Departamento departamentoNaoExiste =
	 * departamentoRepository.findById(departamento.getIdDepartamento());
	 * if(departamentoNaoExiste != null) { try { return new
	 * ResponseEntity<Departamento>(departamentoRepository.save(departamento),
	 * HttpStatus.CREATED); } catch (Exception e) { throw new
	 * ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
	 * "Erro interno, contacte o administrador do sistema!"); }
	 * 
	 * }else { //throw new NegocioException("Usuário não encontrado no sistema!");
	 * //return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR); throw
	 * new ResponseStatusException(HttpStatus.BAD_REQUEST,
	 * "Departamento não existe!"); } }
	 */

	@PutMapping("/departamento/mudar-status")
	public ResponseEntity<String> updateStatusDepartamento(@Valid @RequestBody Departamento departamento) {
		Departamento departamentoDisable = departamentoRepository.findById(departamento.getIdDepartamento());
		// System.out.println("departamento: " + departamento + " DepartamentoDTO: " +
		// departamento);
		if (departamentoDisable != null) {
			try {

				if (departamentoDisable.isAtivo()) {
					departamentoDisable.setAtivo(false);
				} else {
					departamentoDisable.setAtivo(true);
				}
				departamentoRepository.save(departamentoDisable);
				return new ResponseEntity<>(HttpStatus.CREATED);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Erro interno, contacte o administrador do sistema!");
			}

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não existe!");
		}
	}
}
