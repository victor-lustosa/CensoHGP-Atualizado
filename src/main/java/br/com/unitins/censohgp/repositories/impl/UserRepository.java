package br.com.unitins.censohgp.repositories.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.censohgp.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    @Query(" select u from User u order by u.name asc" )
    List<User> findAllOrderByName();

    @Query(" select u from User u where upper(u.registration) like upper(:registration)" )
    User findByRegistration(@Param("registration") String registration);

    @Query(" select u from User u where upper(u.registration) = upper(:registration) and upper(u.email) = upper(:email)" )
    User findByRegistrationAndEmail(@Param("registration") String registration, @Param("email") String email);

    User findByEmail(String email);

    @Query(value = " select * from tb_user u, tb_user_profile x where u.id_user = x.user_id_user and x.profile = :profileId order by u.name asc", nativeQuery = true )
    List<User> findByProfile(int profileId);

    @Query(value = " select * from tb_user u, tb_user_profile x where u.id_user = x.user_id_user and u.isActive = :status order by u.name asc", nativeQuery = true )
    List<User> findByActiveStatus(boolean status);

    @Query(value = " select * from tb_user u, tb_user_profile x where u.id_user = x.user_id_user and x.profile = :profileId and u.isActive = :status order by u.name asc", nativeQuery = true )
    List<User> findAllFilters(int profileId, boolean status);

    @Modifying
    @Query(value = "UPDATE User set password = :password where id_user = :id", nativeQuery = true)
    User updateUserPassword(long userId, String password);
}
