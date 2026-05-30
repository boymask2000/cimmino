package com.cimmino.shop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cimmino.shop.database.Trasportatore;
import com.cimmino.shop.database.TrasportatoreRepository;

@Controller
@RequestMapping("/web/anagrafiche")
public class TrasportatoriController {


	@Autowired
	TrasportatoreRepository trasportatoriRepository;
	


	@GetMapping("/trasportatori")
	public String anagraficaTrasportatori(Model model) {

		model.addAttribute("trasportatori", trasportatoriRepository.findAll());

		return "anagrafiche/anagraficaTrasportatori";
	}

	@GetMapping("/trasportatori/new")
	public String anagraficaTrasportatoriNew(Model model) {

		Trasportatore p = new Trasportatore();
		model.addAttribute("trasportatore", p);

		return "anagrafiche/new_trasportatore";
	}

//	@PostMapping("/products/save")
//	public String saveProduct(@ModelAttribute RysolviaProduct product) {
//
//		repo.save(product);
//
//		return "redirect:/web/handle_products";
//	}
	@PostMapping("/trasportatori/save")
	public String anagraficaTrasportatoriSave(@ModelAttribute Trasportatore trasportatore, Model model) {

		Trasportatore m = trasportatoriRepository.findbyName(trasportatore.getName());
		if (m != null) {
			model.addAttribute("products", trasportatoriRepository.findAll());
			return "anagrafiche/anagraficaMerce";
		}

		trasportatoriRepository.save(trasportatore);
		model.addAttribute("trasportatori", trasportatoriRepository.findAll());
		return "anagrafiche/anagraficaTrasportatori";
	}
	@PostMapping("/trasportatore/update")
	public String anagraficaTrasportatoreUpdate(@ModelAttribute Trasportatore comm, Model model) {

		
		trasportatoriRepository.save(comm);
		model.addAttribute("trasportatori", trasportatoriRepository.findAll());
		return "anagrafiche/anagraficaTrasportatori";
	}
	@GetMapping("/trasportatore/edit/{id}")
	public String anagraficTrasportatoreEdit(@PathVariable Long id, Model model) {
		Optional<Trasportatore> opt = trasportatoriRepository.findById(id);
		Trasportatore comm = opt.get();

		model.addAttribute("trasportatore", comm);

		return "anagrafiche/edit_trasportatore";
	}
	

	
}
