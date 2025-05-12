package br.com.unitins.censohgp.repositories.impl;

import br.com.unitins.censohgp.models.DepartmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<DepartmentModel, Long> {

    Optional<DepartmentModel> findById(long id);

    @Query("select d from DepartmentModel d where upper(d.name) = upper(:name)")
    DepartmentModel findByNameUpperCase(@Param("name") String name);

    // Filter Methods
    @Query(value = "select * from tb_department p where p.internal = :type order by p.name asc", nativeQuery = true)
    List<DepartmentModel> findWithoutStatus(boolean type);

    @Query(value = "select * from tb_department p where p.active = :status order by p.name asc", nativeQuery = true)
    List<DepartmentModel> findWithoutProfile(boolean status);

    @Query(value = "select * from tb_department p where p.internal = :type and p.active = :status order by p.name asc", nativeQuery = true)
    List<DepartmentModel> findAllFilters(boolean type, boolean status);

    @Query("select d from DepartmentModel d order by d.name asc")
    List<DepartmentModel> findAllByName();

    @Query(value = "SELECT * FROM tb_department p WHERE p.active = 'true'", nativeQuery = true)
    List<DepartmentModel> findAllActive();
}
