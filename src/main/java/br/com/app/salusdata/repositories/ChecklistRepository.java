package br.com.app.salusdata.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.app.salusdata.models.ChecklistModel;

public interface ChecklistRepository extends JpaRepository<ChecklistModel, Long> {

    @Query("SELECT c FROM ChecklistModel c JOIN FETCH c.patient p WHERE c.id = :id")
    Optional<ChecklistModel> findChecklistById(long id);

    @Query(value = "SELECT * FROM tb_checklist WHERE id_paciente = ?1", nativeQuery = true)
    List<ChecklistModel> findByPatientId(long patientId);
}
