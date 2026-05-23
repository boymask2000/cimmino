package com.cimmino.shop.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.BinsArriviRepository;
import com.cimmino.shop.database.Vendite;
import com.cimmino.shop.database.VenditeRepository;
import com.cimmino.shop.database.dto.ArriviDTO;
import com.cimmino.shop.database.dto.BinsArriviDTO;
import com.cimmino.shop.mappers.BinsArriviMapper;

@Service
public class AnalisiService {
	@Autowired
	ArriviService arriviService;
	@Autowired
	VenditeRepository venditeRepository;
	@Autowired
	BinsArriviRepository binsArriviRepository;
	@Autowired
	BinsArriviMapper binsArriviMapper;

	public AnalisiResultBean analize(Long idArrivo) {
		ArriviDTO arrivo = arriviService.getById(idArrivo);

		List<Vendite> vendite = venditeRepository.findVenditeDiArrivo(idArrivo);

		AnalisiResultBean result = new AnalisiResultBean();

		List<BinsArrivi> binsArrivi = binsArriviRepository.findByArrivo_Id(idArrivo);

		List<BinsArriviDTO> dtos = binsArriviMapper.toDtoList(binsArrivi);
		arrivo.setBins(dtos);

		BigDecimal totPesoLordoArrivo = binsArrivi.stream()
				.filter(b -> b.getBin() != null && b.getBin().getPesoLordo() != null && b.getNumBins() != null)
				.map(b -> b.getBin().getPesoLordo().multiply(BigDecimal.valueOf(b.getNumBins())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal totPesoNettoArrivo = binsArrivi.stream()
				.filter(b -> b.getBin() != null && b.getBin().getPesoLordo() != null && b.getBin().getTara() != null
						&& b.getNumBins() != null)
				.map(b -> b.getBin().getPesoLordo().subtract(b.getBin().getTara())
						.multiply(BigDecimal.valueOf(b.getNumBins())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal totPesoLordoVendite = BigDecimal.ZERO;
		BigDecimal totPesoNettoVendite = BigDecimal.ZERO;

		for (Vendite ven : vendite) {

			BigDecimal lordo = ven.getBins().stream()
					.filter(b -> b.getBin() != null && b.getBin().getPesoLordo() != null && b.getNumBins() != null)
					.map(b -> b.getBin().getPesoLordo().multiply(BigDecimal.valueOf(b.getNumBins())))
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			totPesoLordoVendite = totPesoLordoVendite.add(lordo);

			BigDecimal netto = ven.getBins().stream()
					.filter(b -> b.getBin() != null && b.getBin().getPesoLordo() != null && b.getBin().getTara() != null
							&& b.getNumBins() != null)
					.map(b -> b.getBin().getPesoLordo().subtract(b.getBin().getTara())
							.multiply(BigDecimal.valueOf(b.getNumBins())))
					.reduce(BigDecimal.ZERO, BigDecimal::add);

			totPesoNettoVendite = totPesoNettoVendite.add(netto);

		}
		result.setArrivo(arrivo);
		result.setVendite(vendite);
		result.setTotPesoLordoArrivo(totPesoLordoArrivo);
		result.setTotPesoNettoArrivo(totPesoNettoArrivo);
		result.setTotPesoLordoVendite(totPesoLordoVendite);
		result.setTotPesoNettoVendite(totPesoNettoVendite);

		return result;
	}

}
