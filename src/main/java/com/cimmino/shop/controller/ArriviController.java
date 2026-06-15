package com.cimmino.shop.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cimmino.shop.service.ArriviService;

@Controller
@RequestMapping("/web/arrivi")
public class ArriviController {
	@Autowired
	ArriviService arriviService;
	
	@PostMapping("/addBin/{id}")
	public String addBin(@PathVariable Long id,
	                     @RequestParam Integer numBins,
	                     @RequestParam Long binId,
	                     @RequestParam BigDecimal pesoLordo,
	                     @RequestParam BigDecimal pesoNetto) {

		arriviService.addBin(
	            id,
	            numBins,
	            binId,
	            pesoLordo,
	            pesoNetto);

	    return "redirect:/web/arrivi/show/" + id;
	}
}
