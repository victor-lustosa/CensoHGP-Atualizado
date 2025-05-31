package br.com.app.salusdata.models.dtos;
import java.util.List;

public record ChecklistDTO(
        long checklistId,
        long patientId,
        String userRegistration,
        String observation,
        List<Long> riskFactors,
        List<Long> incidents,
        List<Long> procedures
) {}