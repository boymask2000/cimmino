package com.cimmino.shop.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsArriviRepository;
import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.Configurazione;
import com.cimmino.shop.database.MerceRepository;
import com.cimmino.shop.mappers.BinMapper;
import com.cimmino.shop.service.ArriviService;
import com.cimmino.shop.service.BinArriviService;
import com.cimmino.shop.service.ConfigurazioneService;
import com.cimmino.shop.service.MovimentiBinService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/web")
public class WebController {
	@Value("${spring.application.name}")
	private String nomeApp;
	private List<Arrivi> risultati = new ArrayList<Arrivi>();
	@Autowired
	ConfigurazioneService configurazioneService;

	@Autowired
	ArriviRepository arriviRepository;
	@Autowired
	BinRepository binRepository;
	@Autowired
	BinsArriviRepository binsArriviRepository;
	@Autowired
	MerceRepository merceRepository;
	@Autowired
	ArriviService arriviService;
	@Autowired
	MovimentiBinService movimentiBinService;
	@Autowired
	CommercianteRepository commercianteRepository;
	@Autowired
	BinMapper binMapper;
	@Autowired
	BinArriviService binArriviService;

	@GetMapping("/home")
	public String home(Model model) {
		LocalDate today = LocalDate.now();

		LocalDate firstDay = today.withDayOfMonth(1);
		LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

		model.addAttribute("startDate", firstDay);
		model.addAttribute("endDate", lastDay);

		Configurazione conf = configurazioneService.getConfigurazione();

		model.addAttribute("configurazione", conf);
		model.addAttribute("nomeApp", nomeApp);
		
		return "home";
	}

	@GetMapping("/anagrafiche")
	public String anagrafiche(Model model) {
		return "anagrafiche";
	}

	@GetMapping("/stampeGlobali")
	public String stampeGlobali(Model model) {
		LocalDate today = LocalDate.now();

		LocalDate firstDay = today.withDayOfMonth(1);
		LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

		model.addAttribute("startDate", firstDay);
		model.addAttribute("endDate", lastDay);

		// model.addAttribute("status", statusService.getStatus());

//		List<User> users = usersRepo.findAll();
//		model.addAttribute("nusers", users.size());

		return "stampeGlobali";
	}

	

	@GetMapping("/filter")
	public String filter(@RequestParam LocalDate startDate, //
			@RequestParam LocalDate endDate, //
			HttpSession session, //
			Model model) {

		if (startDate != null) {
			session.setAttribute("startDate", startDate);
		}
		if (endDate != null) {
			session.setAttribute("endDate", endDate);
		}

		risultati = arriviRepository.cerca(startDate, endDate);

		arriviService.calcSums(risultati);

		model.addAttribute("results", risultati);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		arriviService.calcNumTotaleBins(risultati);

		return "arrivi2";
	}
	@GetMapping("/arrivi")
	public String arrivi(
			HttpSession session, //
			Model model) {
		
		LocalDate startDate = (LocalDate) session.getAttribute("startDate");
		LocalDate endDate = (LocalDate) session.getAttribute("endDate");
		
		risultati = arriviRepository.cerca(startDate, endDate);

		model.addAttribute("results", risultati);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return "arrivi2";
	}

	
	@GetMapping("/info")
	public String info(Model model) {

		return "info";
	}
}