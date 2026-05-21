package com.cimmino.shop.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BinsVenditeRepository extends JpaRepository<BinsVendite, Long> {
	 List<BinsVendite> findByVendita_Id(Long arrId);

}