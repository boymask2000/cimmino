package com.cimmino.shop.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VenditeRepository extends JpaRepository<Vendite, Long> {

//	@Query("SELECT COALESCE(SUM(v.nBins), 0) FROM Vendite v WHERE v.bin.id = :binId")
//	Integer sumBinsSoldByBinId(@Param("binId") Long binId);
	
//	@Query("select v from Vendite v where v.bin.id=:binId and v.arrivo.id=:arrId")
//	List<Vendite> findBinUsati( @Param("binId") Long binId, @Param("arrId") Long arrId) ;
//	
	
	@Query("select v from Vendite v where v.arrivo.id=:arrId")
	List<Vendite> findVenditeDiArrivo( @Param("arrId") Long arrId) ;
//	@Query("select u from User u where u.userid = ?1")
//	User findByUserId(String userid);
	
	@Query("select v from Vendite v where v.commerciante.commerciante_id=:commId")
	List<Vendite> findVenditeDiCommerciante( @Param("commId") Long commId) ;
	
	@Query("select v from Vendite v where v.commerciante.commerciante_id=:commId and v.ddt is null")
	List<Vendite> findVenditeDiCommercianteSenzaDDT( @Param("commId") Long commId) ;
}