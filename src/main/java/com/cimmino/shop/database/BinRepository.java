package com.cimmino.shop.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BinRepository extends JpaRepository<Bin, Long> {

	@Query("select u from Bin  u where u.name = ?1")
	Bin findbyName( String name );
	
//	@Query("""
//			select (a.numBins - coalesce(sum(v.nBins),0))
//			from BinsArrivi a
//			left join Vendite v on v.bin.id = a.bin.id
//			where a.bin.id = :binId
//			group by a.numBins
//			""")
//			Integer getAvailable(Long binId);
	
	@Query("""
			select (b.pesoLordo - b.tara)
			from Bin b
			where b.id = :id
			""")
			Integer getPesoNetto(Long id);

	Bin findByName(String sbin);
}