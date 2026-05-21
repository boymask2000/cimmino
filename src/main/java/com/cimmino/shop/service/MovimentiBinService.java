package com.cimmino.shop.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.BinMovimentoView;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.BinsVendite;
import com.cimmino.shop.database.MovimentiBinRepository;
import com.cimmino.shop.database.MovimentoBin;
import com.cimmino.shop.database.Vendite;

@Service
public class MovimentiBinService {
	@Autowired
	MovimentiBinRepository movimentiBinRepository;

	public void prova() {
		List<BinMovimentoView> vv = movimentiBinRepository.getRiepilogoMovimenti();
		
		for( BinMovimentoView view:vv) {
			System.out.println(view.getName()+" "+view.getSaldo());
		}
		
	}

	public void register(Arrivi arrivo) {
		for( BinsArrivi binArrivo : arrivo.getBins()) {
			MovimentoBin mov = new MovimentoBin();
			mov.setData(LocalDate.now());
			mov.setBin(binArrivo.getBin());
			mov.setInout(1);
			mov.setNumBins(binArrivo.getNumBins());
			movimentiBinRepository.save(mov);
		}
	}
	public void register(Vendite vendita) {
		for( BinsVendite binArrivo : vendita.getBins()) {
			MovimentoBin mov = new MovimentoBin();
			mov.setData(LocalDate.now());
			mov.setBin(binArrivo.getBin());
			mov.setInout(0);
			mov.setNumBins(binArrivo.getNumBins());
			movimentiBinRepository.save(mov);
		}
		
	}
}
