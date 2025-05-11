package br.com.unitins.censohgp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import br.com.unitins.censohgp.models.*;
import br.com.unitins.censohgp.models.enums.Profile;
import br.com.unitins.censohgp.repositories.impl.*;

@Service
public class Dbinit implements CommandLineRunner {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private UserRepository userRepository;

  /*  @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private FatorRiscoRepository fatorRiscoRepository;

    @Autowired
    private IncidenteRepository incidenteRepository;

    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    @Autowired
    private PrecaucaoRepository precaucaoRepoitory;

    @Autowired
    private PacienteRepository pacienteRepoitory;*/

    @Override
    public void run(String... args) throws Exception {

        //Populando tabela usuários
        List<User> usersList = new ArrayList<>();
        User user1 = new User("230995", "Gabrielle Pereira Rocha", "iury@gmail.com",pe.encode("123456"));
        user1.addProfile(Profile.NURSE);
        usersList.add(user1);

        User user2 = new User("2309952", "Brenda Martins Dias", "felipe@gmail.com",pe.encode("123456"));
        user2.addProfile(Profile.ADMIN);
        usersList.add(user2);

        userRepository.saveAll(usersList);
/*
        //Populando tabela departamentos
        List<Departamento> listaDepartamentos = new ArrayList<Departamento>();
        Departamento dep1 = new Departamento("UTI", 20,"", true, true);
        listaDepartamentos.add(dep1);
        Departamento dep2 = new Departamento("PEDIATRIA", 35,"",  true, true);
        listaDepartamentos.add(dep2);
        Departamento dep3 = new Departamento("EMADE", 20,"",  false, false);
        listaDepartamentos.add(dep3);
        Departamento dep4 = new Departamento("CENTRO", 20,"",  false, false);
        listaDepartamentos.add(dep4);

        departamentoRepository.saveAll(listaDepartamentos);

        //Populando tabela incidentes
        List<Incidente> listaIncidentes = new ArrayList<Incidente>();
        Incidente i1 = new Incidente("Flebite","", true);
        listaIncidentes.add(i1);
        Incidente i2 = new Incidente("Erro de medicação","", true);
        listaIncidentes.add(i2);
        Incidente i3 = new Incidente("Extubação acidental","", true);
        listaIncidentes.add(i3);
        Incidente i4 = new Incidente("Paciente admitido com LPP no setor","", true);
        listaIncidentes.add(i4);
        Incidente i5 = new Incidente("Queda de paciente","", true);
        listaIncidentes.add(i5);
        Incidente i6 = new Incidente("Retirada acidental de acesso venoso central","", true);
        listaIncidentes.add(i6);
        Incidente i7 = new Incidente("Retirada acidental de dreno","", true);
        listaIncidentes.add(i7);
        Incidente i8 = new Incidente("Retirada acidental de sondas/GTT/Jejunostomia","", true);
        listaIncidentes.add(i8);

        incidenteRepository.saveAll(listaIncidentes);

        //Populando tabela procedimentos
        List<Procedimento> listaProcedimentos = new ArrayList<Procedimento>();
        Procedimento p1 = new Procedimento("Acesso venoso central","", true);
        listaProcedimentos.add(p1);
        Procedimento p2 = new Procedimento("Drenagem torácica","", true);
        listaProcedimentos.add(p2);
        Procedimento p3 = new Procedimento("Hemodiálise","", true);
        listaProcedimentos.add(p3);
        Procedimento p4 = new Procedimento("SNE/SOE/SNG/SOG/GTT/Jejunostomia","", true);
        listaProcedimentos.add(p4);
        Procedimento p5 = new Procedimento("SVD/Cistostomia","", true);
        listaProcedimentos.add(p5);
        Procedimento p6 = new Procedimento("TOT/TQT","", true);
        listaProcedimentos.add(p6);
        Procedimento p7 = new Procedimento("Transporte de paciente","", true);
        listaProcedimentos.add(p7);

        procedimentoRepository.saveAll(listaProcedimentos);

        //Populando tabela de precauções
        List<Precaucao> listaPrecaucoes = new ArrayList<Precaucao>();
        Precaucao pr1 = new Precaucao("Padrão","", true);
        listaPrecaucoes.add(pr1);
        Precaucao pr2 = new Precaucao("Contato","", true);
        listaPrecaucoes.add(pr2);
        Precaucao pr3 = new Precaucao("Gotículas","", true);
        listaPrecaucoes.add(pr3);
        Precaucao pr4 = new Precaucao("Aerossóis","", true);
        listaPrecaucoes.add(pr4);

        precaucaoRepoitory.saveAll(listaPrecaucoes);

        //Populando tabela fatores de risco
        List<FatorRisco> listaFatoresRisco = new ArrayList<FatorRisco>();
        FatorRisco f1 = new FatorRisco("Paciente com acesso venoso central", "", true);
        listaFatoresRisco.add(f1);
        FatorRisco f2 = new FatorRisco("Paciente com acesso venoso periférico", "", true);
        listaFatoresRisco.add(f2);
        FatorRisco f3 = new FatorRisco("Paciente com dreno de tórax", "", true);
        listaFatoresRisco.add(f3);
        FatorRisco f4 = new FatorRisco("Paciente com ferida operatória", "", true);
        listaFatoresRisco.add(f4);
        FatorRisco f5 = new FatorRisco("Paciente com SNE/SOE/SNG/SOG/GTT/Jejunostomia", "", true);
        listaFatoresRisco.add(f5);
        FatorRisco f6 = new FatorRisco("Paciente com SVD", "", true);
        listaFatoresRisco.add(f6);
        FatorRisco f7 = new FatorRisco("Cistostomia", "", true);
        listaFatoresRisco.add(f7);
        FatorRisco f8 = new FatorRisco("Nefrostomia", "", true);
        listaFatoresRisco.add(f8);
        FatorRisco f9 = new FatorRisco("Paciente em precaução de contato", "", true);
        listaFatoresRisco.add(f9);
        FatorRisco f10 = new FatorRisco("Paciente em precaução respiratória", "", true);
        listaFatoresRisco.add(f10);
        FatorRisco f11 = new FatorRisco("Paciente em precaução reversa", "", true);
        listaFatoresRisco.add(f11);
        FatorRisco f12 = new FatorRisco("Paciente em TOT/TQT", "", true);
        listaFatoresRisco.add(f12);

        fatorRiscoRepository.saveAll(listaFatoresRisco);
        //Populando tabela Pacientes
        List<Paciente> listaPacientes = new ArrayList<Paciente>();
        List<Precaucao> listaPrecaucoesPacientes = new ArrayList<Precaucao>();
        for(int i = 0; i < 2; i++ ){
            Precaucao precPaciente = precaucaoRepoitory.findById(i);
            listaPrecaucoesPacientes.add(precPaciente);
        }

        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Departamento depPaciente1 = departamentoRepository.findById(1);
        Paciente pac1 = new Paciente("2458489523", "Alvaro martins", "Nicole Cavalcanti Sousa",
                "898.870.440-16","25.554.376-1", date ,listaPrecaucoesPacientes,depPaciente1, usuario1);
        pac1.addGenero(Genero.Masculino);
        listaPacientes.add(pac1);
        Departamento depPaciente2 = departamentoRepository.findById(1);
        Paciente pac2 = new Paciente("6169618124", "Cauã Gomes Sousa","Carla Cunha Cavalcanti",
                "797.034.020-20","50.950.308-1", date ,listaPrecaucoesPacientes,depPaciente2, usuario1);
        pac2.addGenero(Genero.Masculino);
        listaPacientes.add(pac2);
        Departamento depPaciente3 = departamentoRepository.findById(2);
        Paciente pac3 = new Paciente("4775586108", "Manuela Azevedo Dias","Sophia Melo Ferreira",
                "855.648.790-40","20.056.532-1", date ,listaPrecaucoesPacientes,depPaciente3, usuario1);
        pac3.addGenero(Genero.Feminino);
        listaPacientes.add(pac3);
        Departamento depPaciente4 = departamentoRepository.findById(1);
        Paciente pac4 = new Paciente("8441689371", "Beatrice Lima Pinto","Luiza Almeida Souza",
                "610.027.380-24","37.653.636-6", date ,listaPrecaucoesPacientes,depPaciente4, usuario1);
        pac4.addGenero(Genero.Feminino);
        listaPacientes.add(pac4);
        Departamento depPaciente5 = departamentoRepository.findById(4);
        Paciente pac5 = new Paciente("3828125963", "Samuel Rodrigues Cavalcanti","Amanda Goncalves Rodrigues",
                "548.006.780-05","24.279.678-3", date ,listaPrecaucoesPacientes,depPaciente5, usuario1);
        pac5.addGenero(Genero.Masculino);
        listaPacientes.add(pac5);
        Departamento depPaciente6 = departamentoRepository.findById(2);
        Paciente pac6 = new Paciente("6192964194", "Rebeca Rodrigues Alves", "Anna Pinto Martins",
                "241.626.140-16","35.945.151-2", date ,listaPrecaucoesPacientes,depPaciente6, usuario1);
        pac6.addGenero(Genero.Feminino);
        listaPacientes.add(pac6);
        Departamento depPaciente7 = departamentoRepository.findById(1);
        Paciente pac7 = new Paciente("5494319641", "Paulo Barbosa Almeida","Fernanda Melo Rodrigues",
                "807.580.190-33","35.133.759-3", date ,listaPrecaucoesPacientes,depPaciente7, usuario1);
        pac7.addGenero(Genero.Masculino);
        listaPacientes.add(pac7);
        Departamento depPaciente8 = departamentoRepository.findById(4);
        Paciente pac8 = new Paciente("6898611892", "Gabriel Melo Barbosa","Melissa Melo Almeida",
                "688.354.660-08", "34.256.662-3", date ,listaPrecaucoesPacientes,depPaciente8, usuario1);
        pac8.addGenero(Genero.Masculino);
        listaPacientes.add(pac8);
        Departamento depPaciente9 = departamentoRepository.findById(4);
        Paciente pac9 = new Paciente("3616734441", "Eduardo Ferreira Pinto","Maria Sousa Azevedo",
                "075.471.260-57","37.907.478-3", date ,listaPrecaucoesPacientes,depPaciente9, usuario1);
        pac9.addGenero(Genero.Masculino);
        listaPacientes.add(pac9);
        Departamento depPaciente10 = departamentoRepository.findById(2);
        Paciente pac10 = new Paciente("151513675", "Luiz Cunha Martins","Isabela Carvalho Gomes",
                "343.720.840-30", "12.441.435-7", date ,listaPrecaucoesPacientes,depPaciente10, usuario1);
        pac10.addGenero(Genero.Masculino);
        listaPacientes.add(pac10);
        Departamento depPaciente11 = departamentoRepository.findById(1);
        Paciente pac11 = new Paciente("925861461", "Kaua Barros Souza", "Melissa Barbosa Carvalho",
                "261.056.650-67", "21.295.585-8", date ,listaPrecaucoesPacientes,depPaciente11, usuario1);
        pac11.addGenero(Genero.Masculino);
        listaPacientes.add(pac11);
        Departamento depPaciente12 = departamentoRepository.findById(3);
        Paciente pac12 = new Paciente("318887963", "Estevan Dias Azevedo","Gabrielle Barros Dias",
                "261.056.650-67", "39.220.441-1", date ,listaPrecaucoesPacientes,depPaciente12, usuario1);
        pac12.addGenero(Genero.Masculino);
        listaPacientes.add(pac12);

        pacienteRepoitory.saveAll(listaPacientes);
*/
    }
}
