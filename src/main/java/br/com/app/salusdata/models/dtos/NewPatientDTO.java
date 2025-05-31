package br.com.app.salusdata.models.dtos;

import java.util.Date;
import java.util.List;

public record NewPatientDTO(
        long patientId,
        String medicalRecord,
        String name,
        String motherName,
        String cpf,
        String rg,
        String gender,
        Date birthDate,
        List<Long> precautions,
        long departmentId
) {}