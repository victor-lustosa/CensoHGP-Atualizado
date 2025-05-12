package br.com.unitins.censohgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.censohgp.model.Incidente;
import br.com.unitins.censohgp.model.Paciente;
import br.com.unitins.censohgp.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{
	
	Paciente findById(long id);

	
	 Paciente findByNome(String nome);
	 
	 Paciente findByProntuario(String prontuario);
	 
	/*
	@Query(value = "SELECT p FROM Usuario p WHERE p.ativo = 'true'")
	List<Usuario> findAllAtivos();
	
	@Query(value = "SELECT p FROM Usuario p WHERE p.ativo = 'false'")
	List<Usuario> findAllInativos();
	

	*/
}
