package com.cimmino.shop.database;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArriviRepository extends JpaRepository<Arrivi, Long> {

	List<Arrivi> findByDataBetween(LocalDate startDate, LocalDate endDate);

	@Query("select u from Arrivi u where u.data>= ?1 and data <=?2 order by data")
	List<Arrivi> cerca(LocalDate startDate, LocalDate endDate);
//	User findByUserId(String userid);

	@Query("select u from Arrivi u where u.key>= ?1 ")
	List<Arrivi> cercaPerInstallation(String installId);

	
	
	@Query(
			  value = "select * from arrivi arr where"
			  		+ " (arr.data>= ?1 and arr .data<= ?2)"
			  		+ " or exists "
			  		+ "( select * from vendite v where v.arrivo_id=arr.arrivo_id and v.data>= ?1 and v.data<= ?2)", 
			  nativeQuery = true)
	List<Arrivi> cercaArriviPerDataVendita(LocalDate startDate, LocalDate endDate);
}
