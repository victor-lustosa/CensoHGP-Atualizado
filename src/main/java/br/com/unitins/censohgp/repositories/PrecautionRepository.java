package br.com.unitins.censohgp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unitins.censohgp.models.PrecautionModel;

public interface PrecautionRepository extends JpaRepository<PrecautionModel, Long> {

    Optional<PrecautionModel> findByName(String name);

    @Query("SELECT p FROM PrecautionModel p ORDER BY p.name ASC")
    List<PrecautionModel> findAllOrderedByName();

    @Query("SELECT p FROM PrecautionModel p WHERE p.isActive = true ORDER BY p.name ASC")
    List<PrecautionModel> findAllActive();

    @Query("SELECT p FROM PrecautionModel p WHERE p.isActive = false ORDER BY p.name ASC")
    List<PrecautionModel> findAllInactive();
}
