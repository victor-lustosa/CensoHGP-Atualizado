package br.com.unitins.censohgp.repositories.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unitins.censohgp.models.PreventionModel;

public interface PreventionRepository extends JpaRepository<PreventionModel, Long> {

    PreventionModel findById(long id); // Já disponível via JpaRepository como findById(id).orElse(null)

    PreventionModel findByName(String name); // nome → name

    @Query("SELECT p FROM Precaution p ORDER BY p.name ASC")
    List<PreventionModel> findAllOrdered(); // nome mais expressivo

    @Query("SELECT p FROM Precaution p WHERE p.active = true ORDER BY p.name ASC")
    List<PreventionModel> findAllActive();

    @Query("SELECT p FROM Precaution p WHERE p.active = false ORDER BY p.name ASC")
    List<PreventionModel> findAllInactive();
}
