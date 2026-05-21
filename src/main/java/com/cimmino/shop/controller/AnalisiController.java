package com.cimmino.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cimmino.shop.service.AnalisiResultBean;
import com.cimmino.shop.service.AnalisiService;

@Controller
@RequestMapping("/web/analisi")
public class AnalisiController {

	@Autowired
	AnalisiService analisiService;

	@GetMapping("/show/{id}")
	public String newAnalisi(@PathVariable Long id, Model model) {
		AnalisiResultBean result = analisiService.analize(id);

		model.addAttribute("analisi", result);
		return "analisi";
	}
}