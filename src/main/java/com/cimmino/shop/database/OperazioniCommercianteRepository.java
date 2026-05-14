package com.cimmino.shop.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OperazioniCommercianteRepository extends JpaRepository<OpCommerciante, Long> {

	
	@Query("select u from OpCommerciante  u where u.commerciante.commerciante_id = ?1")
	List<OpCommerciante> findByCommerciante(long id);
}