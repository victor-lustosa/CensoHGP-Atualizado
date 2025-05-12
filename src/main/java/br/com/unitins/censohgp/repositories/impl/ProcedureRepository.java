package br.com.unitins.censohgp.repositories.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unitins.censohgp.models.ProcedureModel;

public interface ProcedureRepository extends JpaRepository<ProcedureModel, Long> {

    Optional<ProcedureModel> findById(long id); // já implementado como Optional<Procedure> findById(Long id)

    Optional<ProcedureModel> findByName(String name);

    @Query("SELECT p FROM ProcedureModel p ORDER BY p.name ASC")
    List<ProcedureModel> findAllOrdered();

    @Query("SELECT p FROM ProcedureModel p WHERE p.active = true ORDER BY p.name ASC")
    List<ProcedureModel> findAllActive();

    @Query("SELECT p FROM ProcedureModel p WHERE p.active = false ORDER BY p.name ASC")
    List<ProcedureModel> findAllInactive();
}
