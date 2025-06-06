package br.com.app.salusdata.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.app.salusdata.models.IncidentModel;

public interface IncidentRepository extends JpaRepository<IncidentModel, Long> {

    Optional<IncidentModel> findByName(String name);

    @Query("SELECT i FROM IncidentModel i ORDER BY i.name ASC")
    List<IncidentModel> findAllOrderedByName();

    @Query("SELECT i FROM IncidentModel i WHERE i.isActive = true ORDER BY i.name ASC")
    List<IncidentModel> findAllActive();

    @Query("SELECT i FROM IncidentModel i WHERE i.isActive = false ORDER BY i.name ASC")
    List<IncidentModel> findAllInactive();
}
