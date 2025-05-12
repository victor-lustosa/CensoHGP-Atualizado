package br.com.unitins.censohgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.censohgp.model.FatorRisco;
import br.com.unitins.censohgp.model.Incidente;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidenteRepository extends JpaRepository<Incidente, Long>{
	Incidente findById(long id);

    @Query(" select i from Incidente i where upper( i.nome ) like upper( :nome )" )
    List<Incidente> findBySearch( @Param("nome") String nome);
    
    Incidente findByNome(String nome);
    
    @Query(" select i from Incidente i order by i.nome asc" )
    List<Incidente> findAll();
}
