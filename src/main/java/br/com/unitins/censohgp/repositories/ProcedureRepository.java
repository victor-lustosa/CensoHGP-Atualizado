package br.com.unitins.censohgp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unitins.censohgp.models.ProcedureModel;

public interface ProcedureRepository extends JpaRepository<ProcedureModel, Long> {

    Optional<ProcedureModel> findByName(String name);

    @Query("SELECT p FROM ProcedureModel p ORDER BY p.name ASC")
    List<ProcedureModel> findAllByName();

    @Query("SELECT p FROM ProcedureModel p WHERE p.isActive = true ORDER BY p.name ASC")
    List<ProcedureModel> findAllActive();

    @Query("SELECT p FROM ProcedureModel p WHERE p.isActive = false ORDER BY p.name ASC")
    List<ProcedureModel> findAllInactive();
}
