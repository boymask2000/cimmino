package com.cimmino.shop.database;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BinsArriviRepository extends JpaRepository<BinsArrivi, Long> {

	@Query("SELECT COALESCE(SUM(b.numBins), 0) FROM BinsArrivi b WHERE b.bin.id = :binId and b.arrivo.id=:arrId")
	int sumByBinId(@Param("binId") Long binId, @Param("arrId") Long arrId);
	
	
	@Query("SELECT A from BinsArrivi A where A.bin.id=:binId and A.arrivo.id=:arrId")
	List<BinsArrivi> findOccu(@Param("binId") Long binId, @Param("arrId") Long arrId);
	
	 Optional<BinsArrivi> findByBin_Id(Long binId);
	 
	 List<BinsArrivi> findByArrivo_Id(Long arrId);
}