package com.cimmino.shop.service;

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
import com.cimmino.shop.database.BinsGruppoVendita;
import com.cimmino.shop.database.BinsVendite;
import com.cimmino.shop.database.BinsVenditeRepository;
import com.cimmino.shop.database.GruppoVendite;
import com.cimmino.shop.database.MovimentiBinRepository;
import com.cimmino.shop.database.MovimentoBin;
import com.cimmino.shop.database.Vendita;
import static com.cimmino.shop.database.MovimentiBinSpecification.*;
@Service
public class MovimentiBinService {
	@Autowired
	MovimentiBinRepository movimentiBinRepository;
	@Autowired
	BinsArriviRepository binsArriviRepository;
	@Autowired
	BinsVenditeRepository binsVenditeRepository;
	@Autowired
	BinRepository binRepository;

	public void prova() {
		List<BinMovimentoView> vv = movimentiBinRepository.getRiepilogoMovimenti();

		for (BinMovimentoView view : vv) {
			System.out.println(view.getName() + " " + view.getSaldo());
		}

	}

	public void register(Arrivi arrivo) {
		List<BinsArrivi> binsA = binsArriviRepository.findByArrivo_Id(arrivo.getId());
		arrivo.setBins(binsA);

		for (BinsArrivi binArrivo : arrivo.getBins()) {

			MovimentoBin mov = new MovimentoBin();

			Optional<Bin> optBin = binRepository.findById(binArrivo.getBin().getId());
			if (optBin.isPresent()) {
				Bin bin = optBin.get();
				mov.setBinName(bin.getName());
			}

			mov.setData(arrivo.getData());

			mov.setInout(1);
			mov.setNumBins(binArrivo.getNumBins());
			movimentiBinRepository.save(mov);
		}
	}

	public void register(Vendita vendita) {
		for (BinsVendite binArrivo : vendita.getBins()) {
			MovimentoBin mov = new MovimentoBin();
			mov.setData(vendita.getData());
			mov.setBinName(binArrivo.getBin().getName());
			mov.setInout(0);
			mov.setNumBins(binArrivo.getNumBins());
			movimentiBinRepository.save(mov);
		}
	}

	public void register(List<BinsArrivi> binsarrivi, Long operazione) {
		for (BinsArrivi binArrivo : binsarrivi) {
			MovimentoBin mov = new MovimentoBin();
			mov.setData(LocalDate.now());
			mov.setBinName(binArrivo.getBin().getName());
			mov.setInout(operazione.intValue());
			mov.setNumBins(binArrivo.getNumBins());
			movimentiBinRepository.save(mov);
		}
	}

	public List<MovimentoBin> findFiltered(LocalDate dataDa, LocalDate dataA, String bin) {

		 Specification<MovimentoBin> spec =
	                Specification.where(dataDa(dataDa))
	                             .and(dataA(dataA))
	                             .and(bin(bin));

	        return movimentiBinRepository.findAll(spec);

	}

	public void unregister(Arrivi arrivo, String descr) {
		List<BinsArrivi> binsA = binsArriviRepository.findByArrivo_Id(arrivo.getId());
		arrivo.setBins(binsA);

		for (BinsArrivi binArrivo : arrivo.getBins()) {

			MovimentoBin mov = new MovimentoBin();

			Optional<Bin> optBin = binRepository.findById(binArrivo.getBin().getId());
			if (optBin.isPresent()) {
				Bin bin = optBin.get();
				mov.setBinName(bin.getName());
			}

			mov.setData(LocalDate.now());

			mov.setInout(0);
			mov.setDescrizione(descr);
			mov.setNumBins(binArrivo.getNumBins());
			movimentiBinRepository.save(mov);
		}
		
	}

	public void unregister(Vendita vendita, String descr) {
//		List<BinsVendite> binsV = binsVenditeRepository.findByVendita_Id(vendita.getId());
//		vendita.setBins(binsV);

		for (BinsVendite binArrivo : vendita.getBins()) {

			MovimentoBin mov = new MovimentoBin();

			Optional<Bin> optBin = binRepository.findById(binArrivo.getBin().getId());
			if (optBin.isPresent()) {
				Bin bin = optBin.get();
				mov.setBinName(bin.getName());
			}

			mov.setData(LocalDate.now());

			mov.setInout(1);
			mov.setDescrizione(descr);
			mov.setNumBins(binArrivo.getNumBins());
			movimentiBinRepository.save(mov);
		}
		
		
	}

	public void register(GruppoVendite gr) {
		for (BinsGruppoVendita binArrivo : gr.getBins()) {
			MovimentoBin mov = new MovimentoBin();
			mov.setData(gr.getData());
			mov.setBinName(binArrivo.getBin().getName());
			mov.setInout(0);
			mov.setNumBins(binArrivo.getNumBins());
			movimentiBinRepository.save(mov);
		}
		
	}
}
