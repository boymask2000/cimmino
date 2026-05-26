package com.cimmino.shop.service;

import static com.cimmino.shop.database.MovimentiVuotoSpecification.bin;
import static com.cimmino.shop.database.MovimentiVuotoSpecification.dataA;
import static com.cimmino.shop.database.MovimentiVuotoSpecification.dataDa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinMovimentoView;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.BinsArriviRepository;
import com.cimmino.shop.database.BinsVendite;
import com.cimmino.shop.database.MovimentiVuotiRepository;
import com.cimmino.shop.database.MovimentoVuoto;
import com.cimmino.shop.database.Vendite;
@Service
public class MovimentiVuotoService {
	@Autowired
	MovimentiVuotiRepository movimentiVuotiRepository;
	@Autowired
	BinsArriviRepository binsArriviRepository;
	@Autowired
	BinRepository binRepository;

	public void prova() {
		List<BinMovimentoView> vv = movimentiVuotiRepository.getRiepilogoMovimenti();

		for (BinMovimentoView view : vv) {
			System.out.println(view.getName() + " " + view.getSaldo());
		}

	}

	public void register(Arrivi arrivo) {
		List<BinsArrivi> binsA = binsArriviRepository.findByArrivo_Id(arrivo.getId());
		arrivo.setBins(binsA);

		for (BinsArrivi binArrivo : arrivo.getBins()) {

			MovimentoVuoto mov = new MovimentoVuoto();

			Optional<Bin> optBin = binRepository.findById(binArrivo.getBin().getId());
			if (optBin.isPresent()) {
				Bin bin = optBin.get();
				mov.setBinName(bin.getName());
			}

			mov.setData(LocalDate.now());

			mov.setInout(1);
			mov.setNumBins(binArrivo.getNumBins());
			movimentiVuotiRepository.save(mov);
		}
	}

	public void register(Vendite vendita) {
		for (BinsVendite binArrivo : vendita.getBins()) {
			MovimentoVuoto mov = new MovimentoVuoto();
			mov.setData(LocalDate.now());
			mov.setBinName(binArrivo.getBin().getName());
			mov.setInout(0);
			mov.setNumBins(binArrivo.getNumBins());
			movimentiVuotiRepository.save(mov);
		}
	}

	public void register(List<BinsArrivi> binsarrivi, Long operazione) {
		for (BinsArrivi binArrivo : binsarrivi) {
			MovimentoVuoto mov = new MovimentoVuoto();
			mov.setData(LocalDate.now());
			mov.setBinName(binArrivo.getBin().getName());
			mov.setInout(operazione.intValue());
			mov.setNumBins(binArrivo.getNumBins());
			movimentiVuotiRepository.save(mov);
		}
	}

	public List<MovimentoVuoto> findFiltered(LocalDate dataDa, LocalDate dataA, String bin) {

		 Specification<MovimentoVuoto> spec =
	                Specification.where(dataDa(dataDa))
	                             .and(dataA(dataA))
	                             .and(bin(bin));

	        return movimentiVuotiRepository.findAll(spec);

	}
}
