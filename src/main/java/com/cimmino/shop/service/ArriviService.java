package com.cimmino.shop.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.BinsArriviRepository;
import com.cimmino.shop.database.Vendite;
import com.cimmino.shop.database.dto.ArriviDTO;
import com.cimmino.shop.database.dto.BinsArriviDTO;
import com.cimmino.shop.mappers.ArriviMapper;

import jakarta.transaction.Transactional;

@Service
public class ArriviService {

	@Autowired
	private ArriviRepository arriviRepository;

	@Autowired
	private BinRepository binRepository;

	@Autowired
	BinArriviService binArriviService;

	@Autowired
	private BinsArriviRepository binsArriviRepository;
	@Autowired
	private ArriviMapper arriviMapper;

	public ArriviDTO getById(Long id) {
		Arrivi a = arriviRepository.findById(id).orElseThrow();

		return arriviMapper.toDto(a);
	}

	public void eseguiCalcoli(Arrivi arrivo) {
		BigDecimal totalePesoNetto = BigDecimal.ZERO;
		BigDecimal totalePesoLordo = BigDecimal.ZERO;

		for (BinsArrivi b : arrivo.getBins()) {
			Bin bin = b.getBin();
			totalePesoLordo = totalePesoLordo.add(bin.getPesoLordo());
			totalePesoNetto = totalePesoNetto.add(bin.getPesoLordo().subtract(bin.getTara()));
		}
		arrivo.setPeso_lordo(totalePesoLordo.doubleValue());
		arrivo.setPeso_netto(totalePesoNetto.doubleValue());
	}

	public void calcSums(List<Arrivi> risultati) {
		
		for (Arrivi arr : risultati) {

			int avail = 0;
			Map<String, Double> sums = arr.getSums();

			for (Vendite v : arr.getVendite()) {
				if (sums.get("NETTO") == null)
					sums.put("NETTO", (double) 0);
				if (sums.get("LORDO") == null)
					sums.put("LORDO", (double) 0);
				Double lordo = sums.get("LORDO");
				lordo += v.getPeso_lordo();
				sums.put("LORDO", lordo);
				Double netto = sums.get("NETTO");
				netto += v.getPeso_netto();
				sums.put("NETTO", netto);

			}

			for (BinsArrivi b : arr.getBins()) {
				int k = binArriviService.calcAvail(b.getBin().getId(), arr.getId());
				avail += k;
		
				
			}
			sums.put("AVAIL", (double) avail);
		
			
		}

	}

	@Transactional
	public void save(ArriviDTO dto) {

		Arrivi arrivo = arriviMapper.toEntity(dto);

		arrivo = arriviRepository.save(arrivo);

		if (dto.getBins() != null) {

			for (BinsArriviDTO b : dto.getBins()) {

				BinsArrivi entity = new BinsArrivi();

				entity.setArrivo(arrivo);
				entity.setBin(binRepository.findById(b.getBinId()).orElseThrow());

				entity.setNumBins(b.getNumBins());
				entity.setPesoLordo(b.getPesoLordo());
				entity.setPesoNetto(b.getPesoNetto());

				binsArriviRepository.save(entity);
			}
		}
	}
}