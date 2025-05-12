package br.com.unitins.censohgp.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

import br.com.unitins.censohgp.dto.PacienteDTO;
import br.com.unitins.censohgp.exception.NegocioException;
import br.com.unitins.censohgp.model.Departamento;
import br.com.unitins.censohgp.model.Paciente;
import br.com.unitins.censohgp.model.Precaucao;
import br.com.unitins.censohgp.repository.DepartamentoRepository;
import br.com.unitins.censohgp.repository.PacienteRepository;
import br.com.unitins.censohgp.repository.PrecaucaoRepository;

@RestController
@RequestMapping(value = "/apicensohgp")
public class PacienteResource {

	@Autowired
	PacienteRepository pacienteRepository;
	@Autowired
	DepartamentoRepository departamentoRepository;
	@Autowired
	PrecaucaoRepository precaucaoRepository;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/pacientes")
	public List<Paciente> findAll() {
		return pacienteRepository.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/paciente/id/{idPaciente}")
	public Paciente findById(@PathVariable(value = "idPaciente") long id) {
		return pacienteRepository.findById(id);
	}

	// Aplicando padrão DTO
	@PostMapping("/paciente")
	public ResponseEntity<String> salvar(@RequestBody @Valid PacienteDTO pacienteDto) {
		if (pacienteRepository.findByProntuario(pacienteDto.getProntuario()) != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este paciente já existe no sistema!");
			// return ResponseEntity.noContent().build();
		}
		Departamento dep = new Departamento();
		dep = departamentoRepository.findById(pacienteDto.getDepartamento());
		if (dep == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento informado nao existe no sistema!");
		}
		List<Precaucao> listaPrecaucao = new ArrayList();
		long j = 0;
		Precaucao prec = new Precaucao();
		if (pacienteDto.getPrecaucao() != null) {
			for (int i = 0; i < pacienteDto.getPrecaucao().size(); i++) {
				j = pacienteDto.getPrecaucao().get(i);
				prec = precaucaoRepository.findById(j);
				listaPrecaucao.add(prec);
				System.out.println("Imprimindo aqui desgraçça " + prec);

				System.out.println(
						"Imprimindo indexOF " + "na posicao " + i + pacienteDto.getPrecaucao().indexOf(i) + "/n");

				System.out.println(listaPrecaucao.toString());

			}
		}

			Paciente pac = new Paciente();
			pac.setNome(pacienteDto.getNome());
			pac.setProntuario(pacienteDto.getProntuario());
			pac.setNomeMae(pacienteDto.getNomeMae());
			pac.setCpf(pacienteDto.getCpf());
			pac.setSexoMasculino(pacienteDto.isSexoMasculino());
			pac.setDataNascimento(pacienteDto.getDataNascimento());
			pac.setDepartamento(dep);
			pac.setPrecaucao(listaPrecaucao);
		

		

//		    pac.setChecklist(pacienteDto.getChecklist());
		try {
			pacienteRepository.save(pac);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar!");
		}

		// return ResponseEntity<Paciente>(pacienteRepository.save(dep),
		// HttpStatus.CREATED);
	}

//		
	// Aplicando padrão DTO
	@PutMapping("/paciente")
	public ResponseEntity<Paciente> updatePaciente(@Valid @RequestBody Paciente paciente) {
		Paciente pacienteNaoExiste = pacienteRepository.findById(paciente.getIdPaciente());

		if (pacienteNaoExiste != null) {
			try {
				Paciente pac = new Paciente();

				return new ResponseEntity<Paciente>(pacienteRepository.save(pac), HttpStatus.CREATED);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Erro interno, contacte o administrador do sistema!");
			}

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paciente não existe!");
		}
	}

}
