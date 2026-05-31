package com.cimmino.shop.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.Commerciante;
import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.Configurazione;
import com.cimmino.shop.database.GruppoVendite;
import com.cimmino.shop.database.GruppoVenditeRepository;
import com.cimmino.shop.database.OpCommerciante;
import com.cimmino.shop.database.OperazioniCommercianteRepository;
import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.VenditeRepository;
import com.cimmino.shop.database.dto.VenditaDTO;

import jakarta.transaction.Transactional;

@Service
public class VenditeService {
	@Autowired
	ConfigurazioneService configurazioneService;
	@Autowired
	private VenditeRepository venditeRepository;

	@Autowired
	private CommercianteRepository commercianteRepository;

	@Autowired
	private OperazioniCommercianteRepository opCommercianteRepository;

	@Autowired
	private BinRepository binRepository;;
	@Autowired
	ArriviService arriviService;
	@Autowired
	MovimentiBinService movimentiBinService;
	@Autowired
	ArriviRepository arriviRepository;
	@Autowired
	GruppoVenditeRepository gruppoVenditeRepository;

	@Transactional
	public Vendita save(Vendita vendita, Long commercianteId, 
			Boolean creaGruppo, Boolean joinGruppo, Optional<GruppoVendite> optGrp) {

		Commerciante commerciante = commercianteRepository.findById(commercianteId)
				.orElseThrow(() -> new RuntimeException("Commerciante non trovato"));

//		System.out.println("binId = "+binId);
//		Optional<BinsArrivi> opbarr = binsArriviRepository.findById(binId);
//		BinsArrivi barr = opbarr.get();
//		Bin bin = barr.getBin();//.findById(barr.getBin().ge).orElseThrow(() -> new RuntimeException("Bin non trovato"));

		vendita.setCommerciante(commerciante);

		eseguiCalcoli(vendita);
		
		Configurazione conf = configurazioneService.getConfigurazione();
		vendita.setKey(conf.getInstallationId());

		Vendita v = venditeRepository.save(vendita);
		movimentiBinService.register(vendita);

		saveOperazioneCommerciante(vendita);
		
		 if(Boolean.TRUE.equals(creaGruppo)) {

		        GruppoVendite gruppo = new GruppoVendite();
		        gruppo.setStatus("0"); //Aperto

		        vendita.setGruppoVendite(gruppo);
		        vendita.setIsMasterGruppo(false);

		        gruppoVenditeRepository.save(gruppo);
		        venditeRepository.save(vendita);
		    }
		 if(Boolean.TRUE.equals(joinGruppo)) {

			 GruppoVendite grp = optGrp.get();

		        vendita.setGruppoVendite(grp);
		        vendita.setIsMasterGruppo(false);
		        gruppoVenditeRepository.save(grp);
		        venditeRepository.save(vendita);
		    }

		return v;
	}

	private void saveOperazioneCommerciante(Vendita vendita) {
		OpCommerciante op = new OpCommerciante();

		// op.setBin(vendita.getBin());
		op.setCommerciante(vendita.getCommerciante());
		op.setData(vendita.getData());
		op.setDdt(vendita.getDdt());
		op.setImporto(vendita.getImporto());
		op.setLordo(vendita.getPeso_lordo());
		op.setPrezzo(vendita.getPrezzo());
		op.setNettoDiTara(vendita.getNettoDiTara());
		op.setNettoDiScarto(vendita.getNettoDiScarto());
		op.setTara(vendita.getTara());
		// op.setnBins(vendita.getnBins());
		op.setMerce(vendita.getArrivo().getMerce());

		opCommercianteRepository.save(op);

	}

	public void eseguiCalcoli(Vendita vendita) {
		int totaleBins = vendita.getBins().stream().mapToInt(b -> b.getNumBins()).sum();
		BigDecimal pesoLordo = vendita.getPeso_lordo();

		BigDecimal media = BigDecimal.ZERO;

		if (totaleBins > 0 && pesoLordo!=null) {
			media = pesoLordo.divide(BigDecimal.valueOf(totaleBins), 2, RoundingMode.HALF_UP);

		}
		vendita.setMedia(media);
	}

	public void save(VenditaDTO dto) {

		Vendita v = new Vendita();

		v.setCommerciante(new Commerciante(dto.getCommercianteId()));
		// v.setBin(binRepository.getReferenceById(dto.getBinId()));
		v.setArrivo(arriviRepository.getReferenceById(dto.getArrivoId()));

		// v.setnBins(dto.getnBins());
		v.setPrezzo(dto.getPrezzo());
		v.setScarto(dto.getScarto());

		venditeRepository.save(v);
	}
	
	public void save(Vendita v) {


		venditeRepository.save(v);
	}
}