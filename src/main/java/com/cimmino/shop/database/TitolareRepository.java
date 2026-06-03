package com.cimmino.shop.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TitolareRepository extends JpaRepository<Titolare, Long> {

	@Query("select u from Titolare  u where u.name = ?1")
	Titolare findbyName( String name );
}