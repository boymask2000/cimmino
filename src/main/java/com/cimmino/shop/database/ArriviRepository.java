package com.cimmino.shop.database;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArriviRepository extends JpaRepository<Arrivi, Long> {

	 List<Arrivi> findByDataBetween(LocalDate startDate, LocalDate endDate);
	 
//	@Query("select u from User u where u.userid = ?1")
//	User findByUserId(String userid);
}
