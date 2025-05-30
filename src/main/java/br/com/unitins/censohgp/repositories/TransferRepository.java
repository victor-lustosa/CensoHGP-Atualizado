package br.com.unitins.censohgp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unitins.censohgp.models.TransferModel;

public interface TransferRepository extends JpaRepository<TransferModel, Long> {

    @Query("SELECT t FROM TransferModel t WHERE t.patient.id = ?1 ORDER BY t.transferDate DESC")
    List<TransferModel> findByPatientId(long patientId);

}