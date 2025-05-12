package br.com.unitins.censohgp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unitins.censohgp.model.TipoUsuario;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long>{
	
	TipoUsuario findById(long id);
	TipoUsuario findByNome(String nome);
}
