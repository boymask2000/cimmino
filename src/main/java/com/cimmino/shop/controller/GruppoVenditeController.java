package com.cimmino.shop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimmino.shop.database.BinsVendite;
import com.cimmino.shop.database.BinsVenditeRepository;
import com.cimmino.shop.database.GruppoVendite;
import com.cimmino.shop.database.GruppoVenditeRepository;
import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.VenditeRepository;
import com.cimmino.shop.service.VenditeService;

@Controller
@RequestMapping("/gruppovendite")
public class GruppoVenditeController {
	@Autowired
	VenditeRepository venditeRepository;
	@Autowired
	GruppoVenditeRepository gruppoVenditeRepository;
	@Autowired
	BinsVenditeRepository binsVenditeRepository;
	@Autowired
	VenditeService venditeService;

	@GetMapping("/show/{id}")
	public String showGruppo(@PathVariable Long id, Model model) {
		Optional<Vendita> opven = venditeRepository.findById(id);
		if (opven.isEmpty())
			return "";
		Vendita ven = opven.get();
		GruppoVendite gruppo = ven.getGruppoVendite();

		model.addAttribute("gruppo", gruppo);

		return "handle_gruppo";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute GruppoVendite gr, Model model) {
		GruppoVendite gruppo = gruppoVenditeRepository.findById(gr.getId()).get();
		List<Vendita> vendite = venditeRepository.findByGruppoVendite(gruppo);

		List<BinsVendite> bins = new ArrayList<>();
		Vendita unaVendita = vendite.get(0);
		for (Vendita ven : vendite) {

			bins.addAll(ven.getBins());
		}

		Vendita venditaTotale = new Vendita();
		venditaTotale.setGruppoVendite(gruppo);
		venditaTotale.setIsMasterGruppo(true);
		venditaTotale.setArrivo(unaVendita.getArrivo());
		venditaTotale.setBins(bins);
		venditaTotale.setPeso_lordo(gr.getPesoLordoTotale());
		venditaTotale.setCommerciante(unaVendita.getCommerciante());
		venditaTotale.setData(unaVendita.getData());
		venditaTotale = venditeRepository.save(venditaTotale);
		
	

		for (Vendita ven : vendite) {
			List<BinsVendite> binss = ven.getBins();
			for (BinsVendite binVen : binss) {
				binVen.setVendita(venditaTotale);
				binsVenditeRepository.save(binVen);
			}
			bins.addAll(ven.getBins());
		}
		venditaTotale=venditeRepository.save(venditaTotale);
		venditeService.saveOperazioneCommerciante(venditaTotale);
		gruppo.getVendite().add(venditaTotale);
		model.addAttribute("gruppo", gruppo);
		
		
		gruppo.setStatus("1");
		gruppo.setPesoLordoTotale(gr.getPesoLordoTotale());
		gruppoVenditeRepository.save(gruppo);
		return "handle_gruppo";
	}
//
//	@GetMapping("/merge")
//	public String merge(Model model) {
//
//		Master master = new Master();
//
//		model.addAttribute("master", master);
//
//		return "getMasterAddress";
//	}
	
	@GetMapping("/presente/{commercianteId}")
	@ResponseBody
	public boolean gruppoVenditePresente(
	        @PathVariable Long commercianteId) {

	    return gruppoVenditeRepository
	            .existsByCommercianteIdAndStatus(
	                    commercianteId, 0);
	}

}