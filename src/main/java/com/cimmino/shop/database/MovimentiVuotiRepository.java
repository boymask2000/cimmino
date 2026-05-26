package com.cimmino.shop.database;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovimentiVuotiRepository extends JpaRepository<MovimentoVuoto, Long>,
JpaSpecificationExecutor<MovimentoVuoto> {

	@Query("""
			SELECT 
			    b.id as id,
			    b.name as name,

			    SUM(CASE WHEN m.inout = 1 THEN m.numBins ELSE 0 END) as totaleIn,

			    SUM(CASE WHEN m.inout = 0 THEN m.numBins ELSE 0 END) as totaleOut,

			    SUM(CASE WHEN m.inout = 1 THEN m.numBins ELSE -m.numBins END) as saldo

			FROM Bin b
			LEFT JOIN MovimentoVuoto m ON m.binName = b.name
			GROUP BY b.id, b.name
			""")
			List<BinMovimentoView> getRiepilogoMovimenti();
	
	
	@Query("""
		    SELECT o
		    FROM MovimentoVuoto o
		    WHERE (:dataDa IS NULL OR o.data >= :dataDa)
		      AND (:dataA IS NULL OR o.data <= :dataA)
		      AND (:bin IS NULL OR :bin='' OR o.binName = :bin)
		""")
	List<MovimentoBin> filter(
		    @Param("dataDa") LocalDate dataDa,
		    @Param("dataA") LocalDate dataA,
		    @Param("bin") String bin
		);
}
