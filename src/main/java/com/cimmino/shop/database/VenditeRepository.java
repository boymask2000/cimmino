package com.cimmino.shop.database;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VenditeRepository extends JpaRepository<Vendita, Long> {

//	@Query("SELECT COALESCE(SUM(v.nBins), 0) FROM Vendite v WHERE v.bin.id = :binId")
//	Integer sumBinsSoldByBinId(@Param("binId") Long binId);
	
//	@Query("select v from Vendite v where v.bin.id=:binId and v.arrivo.id=:arrId")
//	List<Vendite> findBinUsati( @Param("binId") Long binId, @Param("arrId") Long arrId) ;
//	
	
	@Query("select v from Vendita v where v.arrivo.id=:arrId")
	List<Vendita> findVenditeDiArrivo( @Param("arrId") Long arrId) ;
//	@Query("select u from User u where u.userid = ?1")
//	User findByUserId(String userid);
	
	@Query("select v from Vendita v where v.commerciante.commerciante_id=:commId"
			+ " and ( v.gruppoVendite is null or v.isMasterGruppo =true)")
	List<Vendita> findVenditeDiCommerciante( @Param("commId") Long commId) ;
	
	@Query("select v from Vendita v where v.commerciante.commerciante_id=:commId and v.ddt is null"
			+ " and ( v.gruppoVendite is null or v.isMasterGruppo =true)")
	List<Vendita> findVenditeDiCommercianteSenzaDDT( @Param("commId") Long commId) ;
	
	List<Vendita> findByGruppoVendite(GruppoVendite gruppoVendite);
	
	@Query("select u from Vendita u where u.data>= ?1 and data <=?2 order by data")
	List<Vendita> cerca(LocalDate startDate, LocalDate endDate);
	
	@Query("select u from Vendita  u where u.commerciante.commerciante_id = ?1")
	List<Vendita> findByCommerciante(long id);
}