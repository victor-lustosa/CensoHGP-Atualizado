package br.com.unitins.censohgp.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import br.com.unitins.censohgp.exception.AuthorizationException;
import br.com.unitins.censohgp.exception.ObjectNotFoundException;
import br.com.unitins.censohgp.model.enums.Perfil;
import br.com.unitins.censohgp.security.ControleAutenticacao;
import br.com.unitins.censohgp.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.unitins.censohgp.repository.UsuarioRepository;
import br.com.unitins.censohgp.dto.DepartamentoDTO;
import br.com.unitins.censohgp.dto.UsuarioDTO;
import br.com.unitins.censohgp.exception.NegocioException;
import br.com.unitins.censohgp.model.Departamento;
import br.com.unitins.censohgp.model.Procedimento;
import br.com.unitins.censohgp.model.TipoDepartamento;
import br.com.unitins.censohgp.model.TipoUsuario;
import br.com.unitins.censohgp.model.Usuario;

@RestController
@RequestMapping(value = "/apicensohgp")
public class UsuarioResource {
	    @Autowired
		private BCryptPasswordEncoder pe;
		@Autowired
		private UsuarioRepository repo;

		@Autowired
		private UsuarioRepository usuarioRepository;

		@PreAuthorize("hasAnyRole('Administrador')")
		@ResponseStatus(HttpStatus.OK)
		@GetMapping("/usuarios")

		public List<Usuario> findAll(){
//			ControleAutenticacao user = UsuarioAutenticadoService.authenticated();
//			if (user==null || !user.hasRole(Perfil.Administrador)) {
//				throw new AuthorizationException("Acesso negado");
//			}
			return usuarioRepository.findAll();
		}
		public Usuario findByEmail(String email) {
		ControleAutenticacao user = UsuarioAutenticadoService.authenticated();
		if (user == null || !user.hasRole(Perfil.Administrador) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Usuario.class.getName());
		}
		return obj;
	}
		@PreAuthorize("hasAnyRole('Administrador')")
		@ResponseStatus(HttpStatus.OK)
		@GetMapping("/usuario/{idUsuario}")
		public Usuario findById(@PathVariable(value = "idUsuario") long id) {
			return usuarioRepository.findById(id);
		}

		@PreAuthorize("hasAnyRole('Administrador')")
		@ResponseStatus(HttpStatus.OK)
		@GetMapping("/usuario/matricula/{matricula}")
		public Usuario findByMatricula(@PathVariable(value = "matricula") String matricula) {
			return usuarioRepository.findByMatricula(matricula);
		}

		@PreAuthorize("hasAnyRole('Administrador')")
		@ResponseStatus(HttpStatus.OK)
		@GetMapping("/usuario/nome/{nome}")
		public  List<Usuario>  findBySearch( @PathVariable(value = "nome", required = false) String nome) {
			return usuarioRepository.findBySearch("%"+nome+"%");
		}
		
		/*
		@PostMapping("/usuario")
		public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {
			Usuario usuarioExistente = usuarioRepository.findByMatricula(usuario.getMatricula());
			if (usuarioExistente != null) {
				throw new NegocioException("Esta matrícula já existe no sistema!");
			}
			return new ResponseEntity<Usuario>(usuarioRepository.save(usuario), HttpStatus.CREATED);
		}*/
		
		//Aplicando padrão DTO
		@PreAuthorize("hasAnyRole('Administrador')")
		@PostMapping("/usuario")
		public ResponseEntity<Usuario> salvar(@RequestBody UsuarioDTO usuarioDto) {
			Usuario usuarioExistente = usuarioRepository.findByMatricula(usuarioDto.getMatricula());
			if (usuarioExistente != null) {
				throw new NegocioException("Esta matrícula já existe no sistema!");
			}
		    Usuario usu = new Usuario();
		    usu.setMatricula(usuarioDto.getMatricula());
		    usu.setNome(usuarioDto.getNome());
		    usu.setEmail(usuarioDto.getEmail());
		    usu.setAtivo(true);
		    usu.setSenha(usuarioDto.getSenha());
			System.out.println(usuarioDto.getPerfil());
		    if(usuarioDto.getPerfil() == 1) {
				usu.addPerfil(Perfil.Administrador);
			}else if(usuarioDto.getPerfil() == 2){
				usu.addPerfil(Perfil.Enfermeiro);
			}else {
				throw new NegocioException("Esse perfil não existe no sistema!");
			}
		    return new ResponseEntity<Usuario>(usuarioRepository.save(usu), HttpStatus.CREATED);
		}

		//Aplicando padrão DTO
		@PreAuthorize("hasAnyRole('Administrador')")
		@PutMapping("/usuario")
		public ResponseEntity<Usuario> updateUsuario(@Valid @RequestBody UsuarioDTO usuarioDto){
			Usuario usuarioNaoExiste = usuarioRepository.findById(usuarioDto.getIdUsuario());
			Usuario usuarioExistente = usuarioRepository.findByMatricula(usuarioDto.getMatricula());
			
			if (usuarioExistente != null && usuarioExistente.getIdUsuario() != usuarioDto.getIdUsuario() ) {
				throw new NegocioException("Esta matrícula já existe no sistema!");
			}
			
			if(usuarioNaoExiste != null ) {
				try {
					Usuario usu = new Usuario();
				    
				    usu.setIdUsuario(usuarioDto.getIdUsuario());
				    usu.setMatricula(usuarioDto.getMatricula());
				    usu.setNome(usuarioDto.getNome());
				    usu.setEmail(usuarioDto.getEmail());
				    usu.setAtivo(usuarioDto.isAtivo());
				    usu.setSenha(usuarioDto.getSenha());
					System.out.println(usuarioDto.getPerfil());
					if(usuarioDto.getPerfil() == 1) {
						usu.addPerfil(Perfil.Administrador);
					}else if(usuarioDto.getPerfil() == 2){
						usu.addPerfil(Perfil.Enfermeiro);
					}else {
						throw new NegocioException("Esse perfil não existe no sistema!");
					}
					return new ResponseEntity<Usuario>(usuarioRepository.save(usu), HttpStatus.CREATED);
				} catch (Exception e) {
					throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
				}
				
			}else {
				//throw new NegocioException("Usuário não encontrado no sistema!");
				//return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário informado não existe!");
			}
		}

		@PreAuthorize("hasAnyRole('Administrador')")
		@PutMapping("/usuario/mudar-status")
		public ResponseEntity<Usuario> updateStatusUsuario(@Valid @RequestBody Usuario usuario){
			Usuario usuarioAlterar = usuarioRepository.findById(usuario.getIdUsuario());

			if(usuarioAlterar != null) {
				try {
					if(usuarioAlterar.isAtivo()) {
						usuarioAlterar.setAtivo(false);
					}else {
						usuarioAlterar.setAtivo(true); 
					}
					return new ResponseEntity<Usuario>(usuarioRepository.save(usuarioAlterar), HttpStatus.CREATED);
				} catch (Exception e) {
					throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
				}
				
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario informado não existe!");
			}
		}
}
