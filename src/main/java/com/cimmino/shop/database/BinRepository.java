package com.cimmino.shop.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BinRepository extends JpaRepository<Bin, Long> {

//	@Query("select u from User u where u.userid = ?1")
//	User findByUserId(String userid);
}