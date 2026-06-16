package com.cimmino.shop.database;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GruppoVenditeRepository extends JpaRepository<GruppoVendite, Long> {

	@Query("select u from GruppoVendite u where u.data>= ?1 and data <=?2 order by data")
	List<GruppoVendite> cerca(LocalDate startDate, LocalDate endDate);

	@Query("select v from GruppoVendite v where v.commerciante.commerciante_id=:commId")
	List<GruppoVendite> findVenditeDiCommerciante(@Param("commId") Long commId);

	@Query("select v from GruppoVendite v where v.commerciante.commerciante_id=:commId and (v.ddt is null or v.ddt ='')")
	List<GruppoVendite> findGruppoVenditeDiCommercianteSenzaDDT(@Param("commId") Long commId);
}
