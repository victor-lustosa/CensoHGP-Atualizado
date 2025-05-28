package br.com.unitins.censohgp.repositories.impl;

import br.com.unitins.censohgp.models.DepartmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<DepartmentModel, Long> {

    @Query("select d from DepartmentModel d where upper(d.name) = upper(:name)")
    Optional<DepartmentModel> findByNameUpperCase(@Param("name") String name);

    @Query(value = "select * from tb_department p where p.isInternal = :type order by p.name asc", nativeQuery = true)
    List<DepartmentModel> findWithoutStatus(boolean type);

    @Query(value = "select * from tb_department p where p.isActive = :status order by p.name asc", nativeQuery = true)
    List<DepartmentModel> findWithoutProfile(boolean status);

    @Query(value = "select * from tb_department p where p.isInternal = :type and p.isActive = :status order by p.name asc", nativeQuery = true)
    List<DepartmentModel> findWithAllFilters(boolean type, boolean status);

    @Query("select d from DepartmentModel d order by d.name asc")
    List<DepartmentModel> findAllByName();

    @Query(value = "SELECT * FROM tb_department p WHERE p.isActive = 'true'", nativeQuery = true)
    List<DepartmentModel> findAllActives();
}
