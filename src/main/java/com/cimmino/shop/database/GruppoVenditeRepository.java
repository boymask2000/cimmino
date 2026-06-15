package com.cimmino.shop.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GruppoVenditeRepository extends JpaRepository<GruppoVendite, Long> {
	
	@Query("select v from GruppoVendite v where v.commerciante.commerciante_id=:commId")
	List<GruppoVendite> findVenditeDiCommerciante(@Param("commId") Long commId);
}
