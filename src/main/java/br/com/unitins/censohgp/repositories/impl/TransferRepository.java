package br.com.unitins.censohgp.repositories.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unitins.censohgp.models.TransferModel;

public interface TransferRepository extends JpaRepository<TransferModel, Long> {

    Optional<TransferModel> findById(long id); // Obs: já existe um findById(Long id) como Optional no JpaRepository

    @Query("SELECT t FROM Transfer t WHERE t.patient.id = ?1 ORDER BY t.transferDate DESC")
    List<TransferModel> findByPatientId(long patientId);

    @Query("SELECT t FROM Transfer t")
    List<TransferModel> findAllTransfers(); // renomeado para ficar mais descritivo
}