package com.cimmino.shop.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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
public class ArriviService {

	@Autowired
	private VenditeRepository venditeRepository;

	@Autowired
	private CommercianteRepository commercianteRepository;

	@Autowired
	private OperazioniCommercianteRepository opCommercianteRepository;

	@Autowired
	private BinRepository binRepository;

	public void eseguiCalcoli(Arrivi arrivo) {
		BigInteger totalePesoNetto = BigInteger.ZERO;
		BigInteger totalePesoLordo = BigInteger.ZERO;

		for (BinsArrivi b : arrivo.getBins()) {
			Bin bin = b.getBin();
			totalePesoLordo = totalePesoLordo.add(BigInteger.valueOf(bin.getPeso_lordo()));
			totalePesoNetto = totalePesoNetto.add(BigInteger.valueOf(bin.getPeso_lordo() - bin.getTara()));
		}
		arrivo.setPeso_lordo(totalePesoLordo.intValue());
		arrivo.setPeso_netto(totalePesoNetto.intValue());
	}

	public void calcSums(List<Arrivi> risultati) {
		for (Arrivi arr : risultati) {
			Map<String, Double> sums = arr.getSums();

			for (Vendite v : arr.getVendite()) {
				if (sums.get("NETTO") == null)
					sums.put("NETTO", (double) 0);
				if (sums.get("LORDO") == null)
					sums.put("LORDO", (double) 0);
				Double lordo = sums.get("LORDO");
				lordo += v.getLordo();
				sums.put("LORDO", lordo);
				Double netto = sums.get("NETTO");
				netto += v.getNetto();
				sums.put("NETTO", netto);

			}
		}

	}
}