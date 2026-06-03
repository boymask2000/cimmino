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
import com.cimmino.shop.database.Titolare;
import com.cimmino.shop.database.TitolareRepository;

@Controller
@RequestMapping("/web/anagrafiche")
public class TitolariController {

	@Autowired
	BinRepository binRepository;
	@Autowired
	TitolareRepository titolariRepository;
	@Autowired
	MerceRepository merceRepository;

	@GetMapping("/titolari")
	public String anagraficaTitolari(Model model) {

		model.addAttribute("titolari", titolariRepository.findAll());

		return "anagrafiche/anagraficaTitolari";
	}

	@GetMapping("/titolari/new")
	public String anagraficaNewTitolari(Model model) {
		Titolare titolare = new Titolare();
		model.addAttribute("titolari", titolariRepository.findAll());
		model.addAttribute("titolare", titolare);

		return "anagrafiche/new_titolare";
	}

	@PostMapping("/titolari/save")
	public String anagraficaTitolareSave(@ModelAttribute Titolare comm, Model model) {

		Titolare m = titolariRepository.findbyName(comm.getName());
		if (m != null) {
			model.addAttribute("titolari", titolariRepository.findAll());
			return "anagrafiche/anagraficaTitolari";
		}

		titolariRepository.save(comm);
		model.addAttribute("titolari", titolariRepository.findAll());
		return "anagrafiche/anagraficaTitolari";
	}

	@GetMapping("/titolari/edit/{id}")
	public String anagraficCommerciantiEdit(@PathVariable Long id, Model model) {
		Optional<Titolare> opt = titolariRepository.findById(id);
		Titolare comm = opt.get();

		model.addAttribute("titolare", comm);

		return "anagrafiche/edit_titolare";
	}

	@PostMapping("/titolari/update")
	public String anagraficaCommerciantiUpdate(@ModelAttribute Titolare comm, Model model) {

		titolariRepository.save(comm);
		model.addAttribute("titolari", titolariRepository.findAll());
		return "anagrafiche/anagraficaTitolari";
	}
}
