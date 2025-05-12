package br.com.unitins.censohgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.censohgp.model.Incidente;
import br.com.unitins.censohgp.model.Procedimento;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long>{
	Procedimento findById(long id);
    @Query(" select p from Procedimento p where upper( p.nome ) like upper( :nome )" )
    List<Procedimento> findBySearch(@Param("nome")String nome);
    
    Procedimento findByNome( String nome);
    
    @Query(" select p from Procedimento p order by p.nome asc" )
    List<Procedimento> findAll();
}
