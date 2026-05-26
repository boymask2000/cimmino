package com.cimmino.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cimmino.shop.database.Configurazione;
import com.cimmino.shop.service.ConfigurazioneService;

@Controller
@RequestMapping("/config")
public class ConfigurazioneController {

	@Autowired
	ConfigurazioneService configurazioneService;

	@GetMapping("/show")
	public String show(Model model) {
	
		Configurazione conf = configurazioneService.getConfigurazione();
		
		model.addAttribute("conf", conf);
	
		return "configurazione";
	}
	@PostMapping("/save")
	public String save(@ModelAttribute("configurazione") Configurazione conf, Model model) {
	
		model.addAttribute("conf", conf);
		
		configurazioneService.save( conf);
	
		return "configurazione";
	}
}