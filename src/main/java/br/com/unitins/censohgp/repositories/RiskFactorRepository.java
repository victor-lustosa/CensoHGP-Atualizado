package br.com.unitins.censohgp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unitins.censohgp.models.RiskFactorModel;

public interface RiskFactorRepository extends JpaRepository<RiskFactorModel, Long> {

    Optional<RiskFactorModel> findByName(String name);

    @Query("SELECT r FROM RiskFactorModel r ORDER BY r.name ASC")
    List<RiskFactorModel> findAllOrderedByName();

    @Query("SELECT r FROM RiskFactorModel r WHERE r.isActive = true ORDER BY r.name ASC")
    List<RiskFactorModel> findAllActive();

    @Query("SELECT r FROM RiskFactorModel r WHERE r.isActive = false ORDER BY r.name ASC")
    List<RiskFactorModel> findAllInactive();
}
