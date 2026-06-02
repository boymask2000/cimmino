package com.cimmino.shop.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsArrivi;
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

	List<Arrivi> risultati = new ArrayList<Arrivi>();

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

	@PostMapping("/arrivi/save")
	public String save(@ModelAttribute Arrivi arrivo, RedirectAttributes redirectAttributes) {

		// ❌ CONTROLLO ERRORE
		if (arrivo.getBins() == null || arrivo.getBins().isEmpty()) {
System.out.println("ERRORE BINS ASSENTI");
			redirectAttributes.addFlashAttribute("msg", "Devi selezionare almeno un Bin prima di salvare");

			return "redirect:/web/arrivi/new";
		}

		// ✔ collega figli al parent
		for (BinsArrivi b : arrivo.getBins()) {
			b.setArrivo(arrivo);
		}
		for (BinsArrivi ba : arrivo.getBins()) {

		    Long binId = ba.getBin().getId();

		    Bin managedBin = binRepository.findById(binId)
		            .orElseThrow(() -> new RuntimeException("Bin non trovato: " + binId));

		    ba.setBin(managedBin);
		    ba.setArrivo(arrivo);
		}
		// arriviService.eseguiCalcoli(arrivo);

		Configurazione conf = configurazioneService.getConfigurazione();
		arrivo.setKey(conf.getInstallationId());


		arriviRepository.save(arrivo);

		movimentiBinService.register(arrivo);

		redirectAttributes.addFlashAttribute("msg", "Arrivo salvato correttamente");

		return "redirect:/web/arrivi/new";
	}

	@GetMapping("/arrivi/new")
	public String newArrivo(Model model) {

		Arrivi arrivo = new Arrivi();
		arrivo.setData(LocalDate.now());
		arrivo.setBins(new ArrayList<>());

		model.addAttribute("arrivo", arrivo);
		model.addAttribute("listamerce", merceRepository.findAll());

		// 🔥 FIX IMPORTANTE: DTO NON ENTITY
		model.addAttribute("binsList", binMapper.toDtoList(binRepository.findAll()));
		model.addAttribute("configurazione",configurazioneService.getConfigurazione());

		return "new_arrivo";
	}
	@GetMapping("/info")
	public String info(Model model) {
		

		return "info";
	}
}