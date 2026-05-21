package com.cimmino.shop.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.Commerciante;
import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.OpCommerciante;
import com.cimmino.shop.database.OperazioniCommercianteRepository;
import com.cimmino.shop.database.Vendite;
import com.cimmino.shop.database.VenditeRepository;
import com.cimmino.shop.database.dto.VenditaDTO;

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
;
	@Autowired
	ArriviService arriviService;
	@Autowired
	MovimentiBinService movimentiBinService;
	@Autowired
	ArriviRepository arriviRepository;

	public Vendite create(VenditaDTO dto) {

		Vendite v = new Vendite();

		v.setArrivo(arriviRepository.findById(dto.getArrivoId()).orElseThrow());

//	        v.setBin(
//	            binRepository.findById(dto.getBinId())
//	                .orElseThrow()
//	        );

		v.setCommerciante(commercianteRepository.findById(dto.getCommercianteId()).orElseThrow());

//	        v.setnBins(dto.getnBins());
		v.setPrezzo(dto.getPrezzo());
		v.setScarto(dto.getScarto());

		// 🔥 business logic centralizzata
		//int available = 0;// binRepository.getAvailable(dto.getBinId());

//	        if (dto.getnBins() > available) {
//	            throw new IllegalStateException("Stock insufficiente");
//	        }

		double peso = binRepository.getPesoNetto(dto.getBinId());

		double importo = dto.getPrezzo()
				// * dto.getnBins()
				* peso * (1 - dto.getScarto() / 100);

		v.setImporto(importo);

		return venditeRepository.save(v);
	}

	@Transactional
	public Vendite save(Vendite vendita, Long commercianteId) {

		Commerciante commerciante = commercianteRepository.findById(commercianteId)
				.orElseThrow(() -> new RuntimeException("Commerciante non trovato"));

//		System.out.println("binId = "+binId);
//		Optional<BinsArrivi> opbarr = binsArriviRepository.findById(binId);
//		BinsArrivi barr = opbarr.get();
//		Bin bin = barr.getBin();//.findById(barr.getBin().ge).orElseThrow(() -> new RuntimeException("Bin non trovato"));

		vendita.setCommerciante(commerciante);

	

		eseguiCalcoli(vendita);

		Vendite v = venditeRepository.save(vendita);
		movimentiBinService.register(vendita);
		
		saveOperazioneCommerciante(vendita);

		return v;
	}

	private void saveOperazioneCommerciante(Vendite vendita) {
		OpCommerciante op = new OpCommerciante();

		// op.setBin(vendita.getBin());
		op.setCommerciante(vendita.getCommerciante());
		op.setData(vendita.getData());
		op.setDtt(vendita.getDtt());
		op.setImporto(vendita.getImporto());
		op.setLordo(vendita.getPeso_lordo());
		op.setPrezzo(vendita.getPrezzo());
		op.setNetto(vendita.getPeso_netto());
		op.setTara(vendita.getTara());
		// op.setnBins(vendita.getnBins());
		op.setMerce(vendita.getArrivo().getMerce());

		opCommercianteRepository.save(op);

	}

	public void eseguiCalcoli(Vendite vendita) {
		int totaleBins = vendita.getBins().stream().mapToInt(b -> b.getNumBins()).sum();
		BigDecimal pesoNetto = BigDecimal.valueOf(vendita.getPeso_netto());

		BigDecimal media = BigDecimal.ZERO;

		if (totaleBins > 0) {
			media = pesoNetto.divide(BigDecimal.valueOf(totaleBins), 2, RoundingMode.HALF_UP);
			
		}
		vendita.setMedia(media);
	}

	public void save(VenditaDTO dto) {

		Vendite v = new Vendite();

		v.setCommerciante(new Commerciante(dto.getCommercianteId()));
		// v.setBin(binRepository.getReferenceById(dto.getBinId()));
		v.setArrivo(arriviRepository.getReferenceById(dto.getArrivoId()));

		// v.setnBins(dto.getnBins());
		v.setPrezzo(dto.getPrezzo());
		v.setScarto(dto.getScarto());

		venditeRepository.save(v);
	}
}