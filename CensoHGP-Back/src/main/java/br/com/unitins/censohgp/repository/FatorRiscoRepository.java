package br.com.unitins.censohgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.censohgp.model.FatorRisco;
import org.springframework.stereotype.Repository;

@Repository
public interface FatorRiscoRepository extends JpaRepository<FatorRisco, Long>{
	
	FatorRisco findById(long id);
    @Query(" select f from FatorRisco f where upper( f.nome ) like upper( :nome )" )
    List<FatorRisco> findBySearch( @Param("nome") String nome);
    
    FatorRisco findByNome(String nome);

    @Query(" select f from FatorRisco f order by f.nome asc" )
    List<FatorRisco> findAll();
}
