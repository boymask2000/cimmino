package com.cimmino.shop.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BinRepository extends JpaRepository<Bin, Long> {

	@Query("select u from Bin  u where u.name = ?1")
	Bin findbyName( String name );
}