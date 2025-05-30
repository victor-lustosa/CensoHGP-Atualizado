package br.com.unitins.censohgp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unitins.censohgp.models.PatientModel;

public interface PatientRepository extends JpaRepository<PatientModel, Long> {

    Optional<PatientModel> findByMedicalRecord(String medicalRecord);

    @Query(value = "SELECT * FROM tb_paciente p WHERE p.id_departamento = ?1", nativeQuery = true)
    List<PatientModel> findByDepartment(long departmentId);
}
