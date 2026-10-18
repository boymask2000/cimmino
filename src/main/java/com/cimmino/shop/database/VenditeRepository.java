package com.cimmino.shop.database;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cimmino.shop.database.dto.RiepilogoVendite;

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
			)
	List<Vendita> findVenditeDiCommercianteSenzaDDT( @Param("commId") Long commId) ;
	
	List<Vendita> findByGruppoVendite(GruppoVendite gruppoVendite);
	
	@Query("select u from Vendita u where u.data>= ?1 and data <=?2 order by data")
	List<Vendita> cerca(LocalDate startDate, LocalDate endDate);
	
	@Query("select u from Vendita  u where u.commerciante.commerciante_id = ?1")
	List<Vendita> findByCommerciante(long id);
	
	


@Query("""
    SELECT NEW com.cimmino.shop.database.dto.RiepilogoVendite(
        v.commerciante.commerciante_id,
        v.commerciante.name,
        SUM(v.importo),
        SUM(v.peso_lordo),
        v.data,
        a.merce.merce_id,
        a.merce.name
    )
    FROM Vendita v
    JOIN v.arrivo a
    WHERE v.data >= :startDate
      AND v.data <= :endDate
    GROUP BY
        v.commerciante.commerciante_id,
        v.commerciante.name,
        v.data,
        a.merce.merce_id,
        a.merce.name
    ORDER BY
        v.data,
        v.commerciante.commerciante_id,
        a.merce.merce_id
    """)
List<RiepilogoVendite> riepilogoPerCommerciante(
    @Param("startDate") LocalDate startDate,
    @Param("endDate") LocalDate endDate
);
@Query("""
	    SELECT NEW com.cimmino.shop.database.dto.RiepilogoVendite(
	        v.commerciante.commerciante_id,
	        v.commerciante.name,
	        SUM(v.importo),
	        SUM(v.peso_lordo),
	        v.data,
	        a.merce.merce_id,
	        a.merce.name
	    )
	    FROM Vendita v
	    JOIN v.arrivo a
	    WHERE :commId=0 or v.commerciante.commerciante_id = :commId
	     
	    GROUP BY
	        v.commerciante.commerciante_id,
	        v.commerciante.name,
	        v.data,
	        a.merce.merce_id,
	        a.merce.name
	    ORDER BY
	        v.data,
	        v.commerciante.commerciante_id,
	        a.merce.merce_id
	    """)
	List<RiepilogoVendite> riepilogoTotalePerCommerciante(
	    @Param("commId") Long commId
	);
}