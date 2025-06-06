package br.com.app.salusdata.services;

import br.com.app.salusdata.models.*;
import br.com.app.salusdata.models.enums.Gender;
import br.com.app.salusdata.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.app.salusdata.models.enums.Profile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class DbInitService implements CommandLineRunner {

    private final BCryptPasswordEncoder pe;

    private final UserRepository userRepository;

    private final DepartmentRepository departmentRepository;

    private final RiskFactorRepository riskFactorRepository;

    private final IncidentRepository incidentRepository;

    private final ProcedureRepository procedureRepository;

    private final PrecautionRepository precautionRepository;

    private final PatientRepository patientRepository;

    @Override
    public void run(String... args) {

        //Populando tabela usuários
        List<UserModel> usersList = List.of(
        new UserModel("230995", "Gabrielle Pereira Rocha", "iury@gmail.com", pe.encode("123456"), Profile.NURSE),
        new UserModel("2309952", "Brenda Martins Dias", "felipe@gmail.com", pe.encode("123456"), Profile.ADMIN)
);
        userRepository.saveAll(usersList);

        //Populando tabela departamentos
        List<DepartmentModel> DepartmentsList = List.of(
                new DepartmentModel("UTI", 20, "", true, true),
                new DepartmentModel("PEDIATRIA", 35, "", true, true),
                new DepartmentModel("EMADE", 20, "", false, false),
                new DepartmentModel("CENTRO", 20, "", false, false)
        );

        departmentRepository.saveAll(DepartmentsList);

        //Populando tabela incidentes
        List<IncidentModel> incidentsList = List.of(
                new IncidentModel("Flebite", "", true),
                new IncidentModel("Erro de medicação", "", true),
                new IncidentModel("Extubação acidental", "", true),
                new IncidentModel("Paciente admitido com LPP no setor", "", true),
                new IncidentModel("Queda de paciente", "", true),
                new IncidentModel("Retirada acidental de acesso venoso central", "", true),
                new IncidentModel("Retirada acidental de dreno", "", true),
                new IncidentModel("Retirada acidental de sondas/GTT/Jejunostomia", "", true)
        );

        incidentRepository.saveAll(incidentsList);

        //Populando tabela procedimentos
        List<ProcedureModel> proceduresList = List.of(
                new ProcedureModel("Acesso venoso central", "", true),
                new ProcedureModel("Drenagem torácica", "", true),
                new ProcedureModel("Hemodiálise", "", true),
                new ProcedureModel("SNE/SOE/SNG/SOG/GTT/Jejunostomia", "", true),
                new ProcedureModel("SVD/Cistostomia", "", true),
                new ProcedureModel("TOT/TQT", "", true),
                new ProcedureModel("Transporte de paciente", "", true)
        );

        procedureRepository.saveAll(proceduresList);

        //Populando tabela de precauções
        List<PrecautionModel> PrecautionsList = List.of(
                new PrecautionModel("Padrão", "", true),
                new PrecautionModel("Contato", "", true),
                new PrecautionModel("Gotículas", "", true),
                new PrecautionModel("Aerossóis", "", true)
        );

        precautionRepository.saveAll(PrecautionsList);

        //Populando tabela fatores de risco
        List<RiskFactorModel> riskFactorsList = List.of(
                new RiskFactorModel("Paciente com acesso venoso central", "", true),
                new RiskFactorModel("Paciente com acesso venoso periférico", "", true),
                new RiskFactorModel("Paciente com dreno de tórax", "", true),
                new RiskFactorModel("Paciente com ferida operatória", "", true),
                new RiskFactorModel("Paciente com SNE/SOE/SNG/SOG/GTT/Jejunostomia", "", true),
                new RiskFactorModel("Paciente com SVD", "", true),
                new RiskFactorModel("Cistostomia", "", true),
                new RiskFactorModel("Nefrostomia", "", true),
                new RiskFactorModel("Paciente em precaução de contato", "", true),
                new RiskFactorModel("Paciente em precaução respiratória", "", true),
                new RiskFactorModel("Paciente em precaução reversa", "", true),
                new RiskFactorModel("Paciente em TOT/TQT", "", true)
        );

        riskFactorRepository.saveAll(riskFactorsList);

        //Populando tabela Pacientes

        List<PrecautionModel> patientPrecautionsList = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            PrecautionModel patientPrev = precautionRepository.findById((long) i).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Precaução não foi encontrado!"));
            patientPrecautionsList.add(patientPrev);
        }

        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        DepartmentModel dep1 = departmentRepository.findById((long) 1).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        DepartmentModel dep2 = departmentRepository.findById((long) 2).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        DepartmentModel dep3 = departmentRepository.findById((long) 3).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        DepartmentModel dep4 = departmentRepository.findById((long) 4).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));

        List<PatientModel> patientsList = List.of(
                new PatientModel("2458489523", "Alvaro martins", "Nicole Cavalcanti Sousa",
                        "898.870.440-16", "25.554.376-1", date, patientPrecautionsList, dep1, usersList.getFirst(), Gender.MALE),

                new PatientModel("6169618124", "Cauã Gomes Sousa", "Carla Cunha Cavalcanti",
                        "797.034.020-20", "50.950.308-1", date, patientPrecautionsList, dep1, usersList.getFirst(), Gender.MALE),

                new PatientModel("4775586108", "Manuela Azevedo Dias", "Sophia Melo Ferreira",
                        "855.648.790-40", "20.056.532-1", date, patientPrecautionsList, dep2, usersList.getFirst(), Gender.FEMALE),

                new PatientModel("8441689371", "Beatrice Lima Pinto", "Luiza Almeida Souza",
                        "610.027.380-24", "37.653.636-6", date, patientPrecautionsList, dep1, usersList.getFirst(), Gender.FEMALE),

                new PatientModel("3828125963", "Samuel Rodrigues Cavalcanti", "Amanda Goncalves Rodrigues",
                        "548.006.780-05", "24.279.678-3", date, patientPrecautionsList, dep4, usersList.getFirst(), Gender.MALE),

                new PatientModel("6192964194", "Rebeca Rodrigues Alves", "Anna Pinto Martins",
                        "241.626.140-16", "35.945.151-2", date, patientPrecautionsList, dep2, usersList.getFirst(), Gender.FEMALE),

                new PatientModel("5494319641", "Paulo Barbosa Almeida", "Fernanda Melo Rodrigues",
                        "807.580.190-33", "35.133.759-3", date, patientPrecautionsList, dep1, usersList.getFirst(), Gender.MALE),

                new PatientModel("6898611892", "Gabriel Melo Barbosa", "Melissa Melo Almeida",
                        "688.354.660-08", "34.256.662-3", date, patientPrecautionsList, dep4, usersList.getFirst(), Gender.MALE),

                new PatientModel("3616734441", "Eduardo Ferreira Pinto", "Maria Sousa Azevedo",
                        "075.471.260-57", "37.907.478-3", date, patientPrecautionsList, dep4, usersList.getFirst(), Gender.MALE),

                new PatientModel("151513675", "Luiz Cunha Martins", "Isabela Carvalho Gomes",
                        "343.720.840-30", "12.441.435-7", date, patientPrecautionsList, dep2, usersList.getFirst(), Gender.MALE),

                new PatientModel("925861461", "Kaua Barros Souza", "Melissa Barbosa Carvalho",
                        "261.056.650-67", "21.295.585-8", date, patientPrecautionsList, dep1, usersList.getFirst(), Gender.MALE),

                new PatientModel("318887963", "Estevan Dias Azevedo", "Gabrielle Barros Dias",
                        "261.056.650-67", "39.220.441-1", date, patientPrecautionsList, dep3, usersList.getFirst(), Gender.MALE));

        patientRepository.saveAll(patientsList);
    }
}
