package com.cimmino.shop.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.Commerciante;
import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.OpCommerciante;
import com.cimmino.shop.database.OperazioniCommercianteRepository;
import com.cimmino.shop.database.Vendite;
import com.cimmino.shop.database.VenditeRepository;

import jakarta.transaction.Transactional;

@Service
public class VenditeService {

	@Autowired
	private VenditeRepository venditeRepository;

	@Autowired
	private CommercianteRepository commercianteRepository;

	@Autowired
	private OperazioniCommercianteRepository opCommercianteRepository;

	@Autowired
	private BinRepository binRepository;
	@Autowired
	ArriviService arriviService;

	@Transactional
	public Vendite save(Vendite vendita, Long commercianteId, Long binId) {

		Commerciante commerciante = commercianteRepository.findById(commercianteId)
				.orElseThrow(() -> new RuntimeException("Commerciante non trovato"));

		Bin bin = binRepository.findById(binId).orElseThrow(() -> new RuntimeException("Bin non trovato"));

		vendita.setCommerciante(commerciante);
		vendita.setBin(bin);
		eseguiCalcoli(vendita);
		saveOperazioneCommerciante(vendita);

		return venditeRepository.save(vendita);
	}

	private void saveOperazioneCommerciante(Vendite vendita) {
		OpCommerciante op = new OpCommerciante();

		op.setBin(vendita.getBin());
		op.setCommerciante(vendita.getCommerciante());
		op.setData(vendita.getData());
		op.setDtt(vendita.getDtt());
		op.setImporto(vendita.getImporto());
		op.setLordo(vendita.getLordo());
		op.setNetto(vendita.getNetto());
		op.setTara(vendita.getTara());
		op.setnBins(vendita.getnBins());

		opCommercianteRepository.save(op);

	}

	public void eseguiCalcoli(Vendite vendita) {
		BigInteger totalePesoNetto = BigInteger.ZERO;
		BigInteger totalePesoLordo = BigInteger.ZERO;

		Bin bin = vendita.getBin();
		int lordo = bin.getPeso_lordo() * vendita.getnBins();
		int netto = (bin.getPeso_lordo() - bin.getTara()) * vendita.getnBins();

		totalePesoLordo = totalePesoLordo.add(BigInteger.valueOf(lordo));
		totalePesoNetto = totalePesoNetto.add(BigInteger.valueOf(netto));

		vendita.setLordo(totalePesoLordo.intValue());
		vendita.setNetto(totalePesoNetto.intValue());
		vendita.setTara(vendita.getnBins()*bin.getTara());
	}
}