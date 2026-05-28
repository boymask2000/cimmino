package com.cimmino.shop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.MerceRepository;
import com.cimmino.shop.service.ArriviService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/web/arrivi")
public class WebControllerArrivi {
	@Autowired
	ArriviRepository arriviRepository;
	@Autowired
	BinRepository binRepository;
	@Autowired
	MerceRepository merceRepository;
	@Autowired
	ArriviService arriviService;

	@GetMapping("/show/{id}")
	public String newArrivo(@PathVariable Long id, Model model) {
		Optional<Arrivi> oparrivo = arriviRepository.findById(id);
		if (oparrivo.isEmpty())
			return "";
		Arrivi arrivo = oparrivo.get();

		model.addAttribute("arrivo", arrivo);

		return "show_arrivo";
	}

	@PostMapping("/delete/{id}")
	public String deleteArrivo( //
			@PathVariable Long id, //
			HttpSession session, //
			Model model, //
			RedirectAttributes redirectAttributes) {
		arriviService.delete(id);

		Object startDate = session.getAttribute("startDate");
		Object endDate = session.getAttribute("endDate");

		redirectAttributes.addAttribute("startDate", startDate);
		redirectAttributes.addAttribute("endDate", endDate);

		return "redirect:/web/filter";
	}
}