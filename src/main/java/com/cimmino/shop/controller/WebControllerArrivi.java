package com.cimmino.shop.controller;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.MerceRepository;
import com.cimmino.shop.database.Vendite;
import com.cimmino.shop.service.ArriviService;



@Controller
@RequestMapping("/web/arrivi")
public class WebControllerArrivi {
	@Autowired
	ArriviRepository arriviRepository ;
	@Autowired
	BinRepository binRepository;
	@Autowired
	MerceRepository merceRepository;
	@Autowired
	ArriviService arriviService;


	@GetMapping("/show/{id}")
	public String newArrivo(@PathVariable Long id,Model model) {
		Optional<Arrivi> oparrivo = arriviRepository.findById(id);
		if( oparrivo.isEmpty())return "";
		Arrivi arrivo = oparrivo.get();
		

	    model.addAttribute("arrivo", arrivo);

	    return "show_arrivo";
	}
}