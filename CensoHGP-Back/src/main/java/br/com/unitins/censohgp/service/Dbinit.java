package br.com.unitins.censohgp.service;

import java.util.ArrayList;
import java.util.List;

import br.com.unitins.censohgp.model.enums.Perfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.unitins.censohgp.model.Departamento;
import br.com.unitins.censohgp.model.FatorRisco;
import br.com.unitins.censohgp.model.Incidente;
import br.com.unitins.censohgp.model.Precaucao;
import br.com.unitins.censohgp.model.Procedimento;
import br.com.unitins.censohgp.model.TipoDepartamento;
import br.com.unitins.censohgp.model.TipoUsuario;
import br.com.unitins.censohgp.model.Usuario;
import br.com.unitins.censohgp.repository.DepartamentoRepository;
import br.com.unitins.censohgp.repository.FatorRiscoRepository;
import br.com.unitins.censohgp.repository.IncidenteRepository;
import br.com.unitins.censohgp.repository.PrecaucaoRepository;
import br.com.unitins.censohgp.repository.ProcedimentoRepository;
import br.com.unitins.censohgp.repository.TipoDepartamentoRepository;
import br.com.unitins.censohgp.repository.TipoUsuarioRepository;
import br.com.unitins.censohgp.repository.UsuarioRepository;

@Service
public class Dbinit implements CommandLineRunner{
	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private TipoUsuarioRepository tipoUsuarioRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TipoDepartamentoRepository tipoDepartamentoRepository;
	
	@Autowired
	private DepartamentoRepository departamentoRepository;
	
	@Autowired
	private FatorRiscoRepository fatorRiscoRepository;
	
	@Autowired
	private IncidenteRepository incidenteRepository;
	
	@Autowired
	private ProcedimentoRepository procedimentoRepository;
	
	@Autowired
	private PrecaucaoRepository precaucaoRepoitory;
	
	
	@Override
	public void run(String... args) throws Exception {
		
//		//Populando tabela tipos de usuário(Só existem dois)
//		List<TipoUsuario> listaTipoUsuario = new ArrayList<TipoUsuario>();
//		TipoUsuario tipoUsuario1 = new TipoUsuario("Administrador", "Todas as permissões", true);
//		listaTipoUsuario.add(tipoUsuario1);
//
//		TipoUsuario tipoUsuario2 = new TipoUsuario("Enfermeiro(a)", "Permissões limitadas", true);
//		listaTipoUsuario.add(tipoUsuario2);
//
//		tipoUsuarioRepository.saveAll(listaTipoUsuario);
		
		//Populando tabela usuários
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		Usuario usuario1 = new Usuario("230995", "Iury", "iury@gmail.com",pe.encode("123456"), true);
		usuario1.addPerfil(Perfil.Enfermeiro);
		listaUsuarios.add(usuario1);
		
		Usuario usuario2 = new Usuario("2309952", "Felipe", "felipe@gmail.com",pe.encode("123456"), true);
		usuario2.addPerfil(Perfil.Administrador);
		listaUsuarios.add(usuario2);
		
		usuarioRepository.saveAll(listaUsuarios);
		
		//Populando tabela tipos de departamento(Só existem dois)
		List<TipoDepartamento> listaTipoDepartamento = new ArrayList<TipoDepartamento>();
		TipoDepartamento interno = new TipoDepartamento("Interno", "Departamentos do HGP", true);
		listaTipoDepartamento.add(interno);
		TipoDepartamento externo = new TipoDepartamento("Externo", "Departamentos fora do HGP", true);
		listaTipoDepartamento.add(externo);
		
		tipoDepartamentoRepository.saveAll(listaTipoDepartamento);
		
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

	}
}
