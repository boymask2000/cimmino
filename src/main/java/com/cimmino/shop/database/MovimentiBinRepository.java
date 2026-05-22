package com.cimmino.shop.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovimentiBinRepository extends JpaRepository<MovimentoBin, Long> {

	@Query("""
			SELECT 
			    b.id as id,
			    b.name as name,

			    SUM(CASE WHEN m.inout = 1 THEN m.numBins ELSE 0 END) as totaleIn,

			    SUM(CASE WHEN m.inout = 0 THEN m.numBins ELSE 0 END) as totaleOut,

			    SUM(CASE WHEN m.inout = 1 THEN m.numBins ELSE -m.numBins END) as saldo

			FROM Bin b
			LEFT JOIN MovimentoBin m ON m.binName = b.name
			GROUP BY b.id, b.name
			""")
			List<BinMovimentoView> getRiepilogoMovimenti();
}
