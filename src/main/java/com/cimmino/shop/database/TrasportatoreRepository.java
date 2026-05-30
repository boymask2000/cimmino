package com.cimmino.shop.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrasportatoreRepository extends JpaRepository<Trasportatore, Long> {

	@Query("select u from Trasportatore  u where u.name = ?1")
	Trasportatore findbyName( String name );
}