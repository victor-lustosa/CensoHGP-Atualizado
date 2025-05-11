package br.com.unitins.censohgp.models.dtos;

import java.util.Date;
import java.util.List;

public record NewPatientDTO(
        long patientId,
        String chartNumber,
        String name,
        String motherName,
        String cpf,
        Date birthDate,
        List<Long> precautions,
        long departmentId
) {}