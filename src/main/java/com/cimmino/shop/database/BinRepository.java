package com.cimmino.shop.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BinRepository extends JpaRepository<Bin, Long> {

//	@Query("select u from User u where u.userid = ?1")
//	User findByUserId(String userid);
}