package com.cimmino.shop.database;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DDTRepository extends JpaRepository<DDT, Long> {

	@Query("select g from DDT g where g.numeroDDT = ?1")
	Optional<DDT> findByNumDDT(String dttNum);

	
	@Query("""
			select count(d) > 0
			from DDT d
			where d.numeroDDT = :numero
			""")
			boolean existsByNumeroDDT(@Param("numero") String numero);

	
}
