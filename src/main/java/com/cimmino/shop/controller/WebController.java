package com.cimmino.shop.controller;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsArrivi;



@Controller
@RequestMapping("/web")
public class WebController {
	@Autowired
	ArriviRepository arriviRepository ;
	@Autowired
	BinRepository binRepository;

	@GetMapping("/home")
	public String home(Model model) {
		LocalDate today = LocalDate.now();

		LocalDate firstDay = today.withDayOfMonth(1);
		LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

		model.addAttribute("startDate", firstDay);
		model.addAttribute("endDate", lastDay);
		
		//model.addAttribute("status", statusService.getStatus());

//		List<User> users = usersRepo.findAll();
//		model.addAttribute("nusers", users.size());

		return "home";
	}
	List<Arrivi> risultati = new ArrayList<Arrivi>();
	@GetMapping("/filter")
	public String filter(
	        @RequestParam LocalDate startDate,
	        @RequestParam LocalDate endDate,
	        Model model) {

		  risultati =
		            arriviRepository.findByDataBetween(startDate, endDate);

		    model.addAttribute("results", risultati);

	    return "arrivi";
	}
	@PostMapping("/arrivi/save")
	public String save(@ModelAttribute Arrivi arrivo,  Model model) {

	    for (BinsArrivi b : arrivo.getBins()) {
	        b.setArrivoEntity(arrivo);
	    }

	    arriviRepository.save(arrivo);
	 

	    model.addAttribute("results", risultati);
	    return "arrivi";
	   // return "redirect:/web/arrivi";
	}
	@GetMapping("/arrivi/new")
	public String newArrivo(Model model) {
		Arrivi arrivo = new Arrivi();
	    arrivo.setData(LocalDate.now()); // 👈 data corrente

	    model.addAttribute("arrivo", arrivo);
	    model.addAttribute("binsList", binRepository.findAll());
	    return "new_arrivo";
	}
}