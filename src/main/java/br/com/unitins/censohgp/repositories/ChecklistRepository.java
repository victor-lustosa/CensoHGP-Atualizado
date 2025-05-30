package br.com.unitins.censohgp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.censohgp.models.ChecklistModel;

public interface ChecklistRepository extends JpaRepository<ChecklistModel, Long> {

    @Query("SELECT c FROM ChecklistModel c JOIN FETCH c.patient p WHERE c.id = :id")
    ChecklistModel findById(@Param("id") long id);

    @Query(value = "SELECT * FROM tb_checklist WHERE id_paciente = ?1", nativeQuery = true)
    Optional<List<ChecklistModel>> findByPatientId(long patientId);
}
