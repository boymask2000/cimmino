package com.cimmino.shop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cimmino.shop.database.Commerciante;
import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.OpCommerciante;
import com.cimmino.shop.database.OperazioniCommercianteRepository;

@Controller
@RequestMapping("/web")
public class WebControllerCommercianti {

	@Autowired
	OperazioniCommercianteRepository operazioniCommercianteRepository;
	@Autowired
	CommercianteRepository commercianteRepository;

	//@GetMapping("/commercianti/lista/{id}")
	public String listaCommercianti(@PathVariable Long id,
			@RequestParam(name = "commercianteId", required = false, defaultValue = "0") Long commercianteId,
			Model model) {
		List<OpCommerciante> ll = new ArrayList<OpCommerciante>();

		model.addAttribute("commercianti", commercianteRepository.findAll());

		System.out.println(commercianteId);
		if (commercianteId == 0) {
			ll = operazioniCommercianteRepository.findAll();
		} else {
			ll = operazioniCommercianteRepository.findByCommerciante(commercianteId);
		}
		model.addAttribute("operazioni", ll);
		return "commercianti";
	}
	@GetMapping("/commercianti/lista/{id}")
	public String listaCommercianti(
	        @RequestParam(required = false, defaultValue = "0") Long commercianteId,
	        RedirectAttributes redirectAttributes) {

	    redirectAttributes.addAttribute("commercianteId", commercianteId);

	    if (commercianteId == 0) {
	        redirectAttributes.addFlashAttribute("msg", "Nessun filtro applicato");
	    } else {
	        redirectAttributes.addFlashAttribute("msg", "Filtri applicati");
	    }

	    return "redirect:/web/commercianti/view";
	}
	@GetMapping("/commercianti/view")
	public String view(
	        @RequestParam(required = false, defaultValue = "0") Long commercianteId,
	        Model model) {

	    List<OpCommerciante> ll;

	    if (commercianteId == 0) {
	        ll = operazioniCommercianteRepository.findAll();
	    } else {
	        ll = operazioniCommercianteRepository.findByCommerciante(commercianteId);
	    }

	    model.addAttribute("operazioni", ll);
	    model.addAttribute("commercianti", commercianteRepository.findAll());
	    model.addAttribute("commercianteId", commercianteId);

	    return "commercianti";
	}
}