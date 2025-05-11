package br.com.unitins.censohgp.services;

import br.com.unitins.censohgp.models.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.unitins.censohgp.models.*;
import br.com.unitins.censohgp.models.enums.Profile;
import br.com.unitins.censohgp.repositories.impl.*;
import org.springframework.web.server.ResponseStatusException;

@Service
public class Dbinit implements CommandLineRunner {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RiskFactorRepository riskFactorRepository;

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private PreventionRepository preventionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void run(String... args) throws Exception {

        //Populando tabela usuários
        List<UserModel> usersList = new ArrayList<>();
        UserModel user1 = new UserModel("230995", "Gabrielle Pereira Rocha", "iury@gmail.com",pe.encode("123456"));
        user1.addProfile(Profile.NURSE);

        UserModel user2 = new UserModel("2309952", "Brenda Martins Dias", "felipe@gmail.com",pe.encode("123456"));
        user2.addProfile(Profile.ADMIN);

        usersList.add(user1);
        usersList.add(user2);

        userRepository.saveAll(usersList);

        //Populando tabela departamentos
        List<DepartmentModel> DepartmentsList = new ArrayList<DepartmentModel>();
        DepartmentModel dep1 = new DepartmentModel("UTI", 20,"", true, true);
        DepartmentModel dep2 = new DepartmentModel("PEDIATRIA", 35,"",  true, true);
        DepartmentModel dep3 = new DepartmentModel("EMADE", 20,"",  false, false);
        DepartmentModel dep4 = new DepartmentModel("CENTRO", 20,"",  false, false);

        DepartmentsList.add(dep1);
        DepartmentsList.add(dep2);
        DepartmentsList.add(dep3);
        DepartmentsList.add(dep4);

        departmentRepository.saveAll(DepartmentsList);

        //Populando tabela incidentes
        List<IncidentModel> incidentsList = new ArrayList<IncidentModel>();
        IncidentModel i1 = new IncidentModel("Flebite","", true);
        IncidentModel i2 = new IncidentModel("Erro de medicação","", true);
        IncidentModel i3 = new IncidentModel("Extubação acidental","", true);
        IncidentModel i4 = new IncidentModel("Paciente admitido com LPP no setor","", true);
        IncidentModel i5 = new IncidentModel("Queda de paciente","", true);
        IncidentModel i6 = new IncidentModel("Retirada acidental de acesso venoso central","", true);
        IncidentModel i7 = new IncidentModel("Retirada acidental de dreno","", true);
        IncidentModel i8 = new IncidentModel("Retirada acidental de sondas/GTT/Jejunostomia","", true);

        incidentsList.add(i8);
        incidentsList.add(i1);
        incidentsList.add(i2);
        incidentsList.add(i3);
        incidentsList.add(i4);
        incidentsList.add(i5);
        incidentsList.add(i6);
        incidentsList.add(i7);

        incidentRepository.saveAll(incidentsList);

        //Populando tabela procedimentos
        List<ProcedureModel> proceduresList = new ArrayList<ProcedureModel>();
        ProcedureModel p1 = new ProcedureModel("Acesso venoso central","", true);
        ProcedureModel p2 = new ProcedureModel("Drenagem torácica","", true);
        ProcedureModel p3 = new ProcedureModel("Hemodiálise","", true);
        ProcedureModel p4 = new ProcedureModel("SNE/SOE/SNG/SOG/GTT/Jejunostomia","", true);
        ProcedureModel p5 = new ProcedureModel("SVD/Cistostomia","", true);
        ProcedureModel p6 = new ProcedureModel("TOT/TQT","", true);
        ProcedureModel p7 = new ProcedureModel("Transporte de paciente","", true);
        proceduresList.add(p1);
        proceduresList.add(p2);
        proceduresList.add(p3);
        proceduresList.add(p4);
        proceduresList.add(p5);
        proceduresList.add(p6);
        proceduresList.add(p7);

        procedureRepository.saveAll(proceduresList);

        //Populando tabela de precauções
        List<PreventionModel> PreventionsList = new ArrayList<PreventionModel>();
        PreventionModel pr1 = new PreventionModel("Padrão","", true);
        PreventionModel pr2 = new PreventionModel("Contato","", true);
        PreventionModel pr3 = new PreventionModel("Gotículas","", true);
        PreventionModel pr4 = new PreventionModel("Aerossóis","", true);
        PreventionsList.add(pr1);
        PreventionsList.add(pr2);
        PreventionsList.add(pr3);
        PreventionsList.add(pr4);

        preventionRepository.saveAll(PreventionsList);

        //Populando tabela fatores de risco
        List<RiskFactorModel> riskFactorsList = new ArrayList<RiskFactorModel>();
        RiskFactorModel f1 = new RiskFactorModel("Paciente com acesso venoso central", "", true);
        RiskFactorModel f2 = new RiskFactorModel("Paciente com acesso venoso periférico", "", true);
        RiskFactorModel f3 = new RiskFactorModel("Paciente com dreno de tórax", "", true);
        RiskFactorModel f4 = new RiskFactorModel("Paciente com ferida operatória", "", true);
        RiskFactorModel f5 = new RiskFactorModel("Paciente com SNE/SOE/SNG/SOG/GTT/Jejunostomia", "", true);
        RiskFactorModel f6 = new RiskFactorModel("Paciente com SVD", "", true);
        RiskFactorModel f7 = new RiskFactorModel("Cistostomia", "", true);
        RiskFactorModel f8 = new RiskFactorModel("Nefrostomia", "", true);
        RiskFactorModel f9 = new RiskFactorModel("Paciente em precaução de contato", "", true);
        RiskFactorModel f10 = new RiskFactorModel("Paciente em precaução respiratória", "", true);
        RiskFactorModel f11 = new RiskFactorModel("Paciente em precaução reversa", "", true);
        RiskFactorModel f12 = new RiskFactorModel("Paciente em TOT/TQT", "", true);
        riskFactorsList.add(f1);
        riskFactorsList.add(f2);
        riskFactorsList.add(f3);
        riskFactorsList.add(f4);
        riskFactorsList.add(f5);
        riskFactorsList.add(f6);
        riskFactorsList.add(f7);
        riskFactorsList.add(f8);
        riskFactorsList.add(f9);
        riskFactorsList.add(f10);
        riskFactorsList.add(f11);
        riskFactorsList.add(f12);

        riskFactorRepository.saveAll(riskFactorsList);

        //Populando tabela Pacientes
        List<PatientModel> patientsList = new ArrayList<PatientModel>();
        List<PreventionModel> patientPreventionsList = new ArrayList<PreventionModel>();
        for(int i = 0; i < 2; i++ ){
            PreventionModel patientPrev = preventionRepository.findById(i);
            patientPreventionsList.add(patientPrev);
        }

        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        DepartmentModel depPatient1 = departmentRepository.findById(1).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        PatientModel pat1 = new PatientModel("2458489523", "Alvaro martins", "Nicole Cavalcanti Sousa",
                "898.870.440-16","25.554.376-1", date ,patientPreventionsList,depPatient1, user1);
        pat1.addGender(Gender.MALE);

        DepartmentModel depPatient2 = departmentRepository.findById(1).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        PatientModel pat2 = new PatientModel("6169618124", "Cauã Gomes Sousa","Carla Cunha Cavalcanti",
                "797.034.020-20","50.950.308-1", date ,patientPreventionsList,depPatient2, user1);
        pat2.addGender(Gender.MALE);

        DepartmentModel depPatient3 = departmentRepository.findById(2).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        PatientModel pat3 = new PatientModel("4775586108", "Manuela Azevedo Dias","Sophia Melo Ferreira",
                "855.648.790-40","20.056.532-1", date ,patientPreventionsList,depPatient3, user1);
        pat3.addGender(Gender.FEMALE);

        DepartmentModel depPatient4 = departmentRepository.findById(1).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        PatientModel pat4 = new PatientModel("8441689371", "Beatrice Lima Pinto","Luiza Almeida Souza",
                "610.027.380-24","37.653.636-6", date ,patientPreventionsList,depPatient4, user1);
        pat4.addGender(Gender.FEMALE);

        DepartmentModel depPatient5 = departmentRepository.findById(4).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        PatientModel pat5 = new PatientModel("3828125963", "Samuel Rodrigues Cavalcanti","Amanda Goncalves Rodrigues",
                "548.006.780-05","24.279.678-3", date ,patientPreventionsList,depPatient5, user1);
        pat5.addGender(Gender.MALE);

        DepartmentModel depPatient6 = departmentRepository.findById(2).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        PatientModel pat6 = new PatientModel("6192964194", "Rebeca Rodrigues Alves", "Anna Pinto Martins",
                "241.626.140-16","35.945.151-2", date ,patientPreventionsList,depPatient6, user1);
        pat6.addGender(Gender.FEMALE);

        DepartmentModel depPatient7 = departmentRepository.findById(1).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        PatientModel pat7 = new PatientModel("5494319641", "Paulo Barbosa Almeida","Fernanda Melo Rodrigues",
                "807.580.190-33","35.133.759-3", date ,patientPreventionsList,depPatient7, user1);
        pat7.addGender(Gender.MALE);

        DepartmentModel depPatient8 = departmentRepository.findById(4).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        PatientModel pat8 = new PatientModel("6898611892", "Gabriel Melo Barbosa","Melissa Melo Almeida",
                "688.354.660-08", "34.256.662-3", date ,patientPreventionsList,depPatient8, user1);
        pat8.addGender(Gender.MALE);

        DepartmentModel depPatient9 = departmentRepository.findById(4).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        PatientModel pat9 = new PatientModel("3616734441", "Eduardo Ferreira Pinto","Maria Sousa Azevedo",
                "075.471.260-57","37.907.478-3", date ,patientPreventionsList,depPatient9, user1);
        pat9.addGender(Gender.MALE);

        DepartmentModel depPatient10 = departmentRepository.findById(2).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        PatientModel pat10 = new PatientModel("151513675", "Luiz Cunha Martins","Isabela Carvalho Gomes",
                "343.720.840-30", "12.441.435-7", date ,patientPreventionsList,depPatient10, user1);
        pat10.addGender(Gender.MALE);

        DepartmentModel depPatient11 = departmentRepository.findById(1).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        PatientModel pat11 = new PatientModel("925861461", "Kaua Barros Souza", "Melissa Barbosa Carvalho",
                "261.056.650-67", "21.295.585-8", date ,patientPreventionsList,depPatient11, user1);
        pat11.addGender(Gender.MALE);

        DepartmentModel depPatient12 = departmentRepository.findById(3).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não foi encontrado!"));
        PatientModel pat12 = new PatientModel("318887963", "Estevan Dias Azevedo","Gabrielle Barros Dias",
                "261.056.650-67", "39.220.441-1", date ,patientPreventionsList,depPatient12, user1);
        pat12.addGender(Gender.MALE);

        patientsList.add(pat1);
        patientsList.add(pat2);
        patientsList.add(pat3);
        patientsList.add(pat4);
        patientsList.add(pat5);
        patientsList.add(pat6);
        patientsList.add(pat7);
        patientsList.add(pat8);
        patientsList.add(pat9);
        patientsList.add(pat10);
        patientsList.add(pat11);
        patientsList.add(pat12);

        patientRepository.saveAll(patientsList);
    }
}
