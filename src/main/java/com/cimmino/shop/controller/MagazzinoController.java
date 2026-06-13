package com.cimmino.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cimmino.shop.service.MagazzinoRow;
import com.cimmino.shop.service.MagazzinoService;

@Controller
@RequestMapping("/magazzino")
public class MagazzinoController {
	@Autowired
	MagazzinoService magazzinoService;

	@GetMapping("/show")
	public String show(Model model) {
		List<MagazzinoRow> rows = magazzinoService.dump();

		model.addAttribute("rows", rows);

		return "magazzino";
	}

	@GetMapping("/dump")
	public String dump(Model model) {

		return "home";
	}
}
