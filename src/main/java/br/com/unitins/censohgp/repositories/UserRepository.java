package br.com.unitins.censohgp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.censohgp.models.UserModel;

@SuppressWarnings("unused")
public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Query(" select u from UserModel u order by u.name asc")
    List<UserModel> findAllByName();

    @Query(" select u from UserModel u where upper(u.registration) like upper(:registration)")
    Optional<UserModel> findByRegistration(@Param("registration") String registration);

    @Query(" select u from UserModel u where upper(u.registration) = upper(:registration) and upper(u.email) = upper(:email)")
    UserModel findByRegistrationAndEmail(@Param("registration") String registration, @Param("email") String email);

    UserModel findByEmail(String email);

    @Query(value = " select * from tb_user u, tb_user_profile x where u.id_user = x.user_id_user and x.profile = :profileId order by u.name asc", nativeQuery = true)
    List<UserModel> findByProfile(int profileId);

    @Query(value = " select * from tb_user u, tb_user_profile x where u.id_user = x.user_id_user and u.isActive = :status order by u.name asc", nativeQuery = true)
    List<UserModel> findByActiveStatus(boolean status);

    @Query(value = " select * from tb_user u, tb_user_profile x where u.id_user = x.user_id_user and x.profile = :profileId and u.isActive = :status order by u.name asc", nativeQuery = true)
    List<UserModel> findAllFilters(int profileId, boolean status);

    @Modifying
    @Query(value = "UPDATE UserModel set password = :password where id_user = :id", nativeQuery = true)
    UserModel updateUserPassword(long userId, String password);
}
