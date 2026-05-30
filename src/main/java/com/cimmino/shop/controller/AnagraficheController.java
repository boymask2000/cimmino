package com.cimmino.shop.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.Commerciante;
import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.Merce;
import com.cimmino.shop.database.MerceRepository;

@Controller
@RequestMapping("/web/anagrafiche")
public class AnagraficheController {

	@Autowired
	BinRepository binRepository;

	@Autowired
	MerceRepository merceRepository;
	
	@Autowired
	CommercianteRepository commercianteRepository;

	@GetMapping("/merce")
	public String anagraficaMerce(Model model) {

		model.addAttribute("products", merceRepository.findAll());

		return "anagrafiche/anagraficaMerce";
	}

	@GetMapping("/merce/new")
	public String anagraficaMerceNew(Model model) {

		Merce p = new Merce();
		model.addAttribute("product", p);

		return "anagrafiche/merceNew";
	}

//	@PostMapping("/products/save")
//	public String saveProduct(@ModelAttribute RysolviaProduct product) {
//
//		repo.save(product);
//
//		return "redirect:/web/handle_products";
//	}
	@PostMapping("/merce/save")
	public String anagraficaMerceSave(@ModelAttribute Merce product, Model model) {

		Merce m = merceRepository.findbyName(product.getName());
		if (m != null) {
			model.addAttribute("products", merceRepository.findAll());
			return "anagrafiche/anagraficaMerce";
		}

		merceRepository.save(product);
		model.addAttribute("products", merceRepository.findAll());
		return "anagrafiche/anagraficaMerce";
	}

	// **********************
	// **********************
	@GetMapping("/bins")
	public String anagraficaBins(Model model) {

		model.addAttribute("products", binRepository.findAll());

		return "anagrafiche/anagraficaBins";
	}

	@GetMapping("/bins/edit/{id}")
	public String anagraficaBinsEdit(@PathVariable Long id, Model model) {
		Bin product = binRepository.findById(id).orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

		model.addAttribute("bin", product);

		return "anagrafiche/edit_bin";
	}

	@GetMapping("/bins/new")
	public String anagraficaBinsNew(Model model) {

		Bin product = new Bin();
		model.addAttribute("bin", product);
	

		return "anagrafiche/new_bin";
	}

	@PostMapping("/bins/update")
	public String anagraficaBinsUpdate(@ModelAttribute Bin product, Model model) {

		binRepository.save(product);
		model.addAttribute("products", binRepository.findAll(Sort.by("name")));
		return "anagrafiche/anagraficaBins";
	}
	@PostMapping("/bins/save")
	public String anagraficaBinSave(@ModelAttribute Bin bin, Model model) {

		Bin m = binRepository.findbyName(bin.getName());
		if (m != null) {
			model.addAttribute("products", merceRepository.findAll());
			return "anagrafiche/anagraficaMerce";
		}
bin.setPesoLordo(BigDecimal.ZERO);
		binRepository.save(bin);
		model.addAttribute("products", binRepository.findAll());
		return "anagrafiche/anagraficaBins";
	}
	
	@GetMapping("/commercianti")
	public String anagraficaCommercianti(Model model) {

		model.addAttribute("commercianti", commercianteRepository.findAll());

		return "anagrafiche/anagraficaCommercianti";
	}
	@GetMapping("/commercianti/new")
	public String anagraficaCommerciantiNew(Model model) {

		Commerciante commerciante = new Commerciante();
		model.addAttribute("commerciante", commerciante);
	

		return "anagrafiche/new_commerciante";
	}
	@PostMapping("/commercianti/save")
	public String anagraficaCommerciantiSave(@ModelAttribute Commerciante comm, Model model) {

		Commerciante m = commercianteRepository.findbyName(comm.getName());
		if (m != null) {
			model.addAttribute("commercianti", commercianteRepository.findAll());
			return "anagrafiche/anagraficaCommercianti";
		}

		commercianteRepository.save(comm);
		model.addAttribute("commercianti", commercianteRepository.findAll());
		return "anagrafiche/anagraficaCommercianti";
	}
	@PostMapping("/commercianti/update")
	public String anagraficaCommerciantiUpdate(@ModelAttribute Commerciante comm, Model model) {

		
		commercianteRepository.save(comm);
		model.addAttribute("commercianti", commercianteRepository.findAll());
		return "anagrafiche/anagraficaCommercianti";
	}
	@GetMapping("/commercianti/edit/{id}")
	public String anagraficCommerciantiEdit(@PathVariable Long id, Model model) {
		Optional<Commerciante> opt = commercianteRepository.findById(id);
		Commerciante comm = opt.get();

		model.addAttribute("commerciante", comm);

		return "anagrafiche/edit_commerciante";
	}

}
