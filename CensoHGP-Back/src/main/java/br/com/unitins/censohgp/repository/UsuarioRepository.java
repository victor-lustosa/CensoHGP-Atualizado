package br.com.unitins.censohgp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.censohgp.model.FatorRisco;
import br.com.unitins.censohgp.model.Incidente;
import br.com.unitins.censohgp.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{


    Usuario findById(long id);
    @Query(" select u from Usuario u where upper( u.nome ) like upper( :nome )" )
    List<Usuario> findBySearch( @Param("nome") String nome);

    Usuario findByNome(String nome);

    @Query(" select u from Usuario u order by u.nome asc" )
    List<Usuario> findAll();

    Usuario findByMatricula(String matricula);
    Usuario findByEmail(String email);

    @Modifying
    @Query(value="UPDATE Usuario set senha = :senha where id_usuario = :idUsuario", nativeQuery=true)
    Usuario updateSenhaUsuario(long idUsuario, String senha);
	/*
	@Query(value = "SELECT p FROM Usuario p WHERE p.ativo = 'true'")
	List<Usuario> findAllAtivos();

	@Query(value = "SELECT p FROM Usuario p WHERE p.ativo = 'false'")
	List<Usuario> findAllInativos();

	@Query(value = "SELECT * FROM Usuario p WHERE p.id_tipousuario = ?1")
	List<Usuario> findByTipoUsuario(long idTipoUsuario);
	*/

}
