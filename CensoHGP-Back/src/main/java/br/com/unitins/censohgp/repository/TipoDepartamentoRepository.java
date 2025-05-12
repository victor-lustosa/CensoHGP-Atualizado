package br.com.unitins.censohgp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unitins.censohgp.model.TipoDepartamento;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDepartamentoRepository extends JpaRepository<TipoDepartamento, Long>{
	
	TipoDepartamento findById(long id);
	TipoDepartamento findByNome(String nome);
}
