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
	
	

	
}
