package com.cimmino.shop.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.Vendite;
import com.cimmino.shop.service.VenditeService;

@Controller
@RequestMapping("/web")
public class WebControllerVendite {
	@Autowired
	ArriviRepository arriviRepository;
	@Autowired
	CommercianteRepository commercianteRepository;
	@Autowired
	BinRepository binRepository;
	@Autowired
	VenditeService venditeService;

	@GetMapping("/vendita/new/{id}")
	public String newVendita(@PathVariable Long id, Model model) {
		Vendite vendita = new Vendite();
		vendita.setData(LocalDate.now()); // 👈 data corrente

		Optional<Arrivi> arr = arriviRepository.findById(id);
		vendita.setArrivoEntity(arr.get());

		model.addAttribute("arrivo", arr.get());
		model.addAttribute("vendita", vendita);
		model.addAttribute("bins", binRepository.findAll());
		model.addAttribute("commercianti", commercianteRepository.findAll());
		model.addAttribute("currData", LocalDate.now());
		return "new_vendita";
	}

	@PostMapping("/vendita/save")
	public String saveVendita( //
			@ModelAttribute("vendita") Vendite vendita, //
			@RequestParam("commerciante.commerciante_id") Long commercianteId,
			@RequestParam("bin.id") Long binId,
			@RequestParam("currData") LocalDate currData) {

		vendita.setData(currData);
		venditeService.save(vendita, commercianteId, binId);

		return  "home";
	}



}