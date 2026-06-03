package com.cimmino.shop.database;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GruppoVenditeRepository extends JpaRepository<GruppoVendite, Long> {

	@Query("select g from GruppoVendite g where g.status = '0' and commercianteId=?1")
	Optional<GruppoVendite> findGroupOpen(Long commercianteId);

	boolean existsByCommercianteIdAndStatus(Long commercianteId, int i);

}
