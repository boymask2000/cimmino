package com.cimmino.shop.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.Configurazione;
import com.cimmino.shop.database.MerceRepository;
import com.cimmino.shop.mappers.BinMapper;
import com.cimmino.shop.service.ArriviService;
import com.cimmino.shop.service.ConfigurazioneService;
import com.cimmino.shop.service.MovimentiBinService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/web/arrivi")
public class WebControllerArrivi {
	@Autowired
	ConfigurazioneService configurazioneService;
	@Autowired
	ArriviRepository arriviRepository;
	@Autowired
	BinRepository binRepository;
	@Autowired
	MerceRepository merceRepository;
	@Autowired
	ArriviService arriviService;
	@Autowired
	MovimentiBinService movimentiBinService;
	@Autowired
	BinMapper binMapper;

	@GetMapping("/show/{id}")
	public String newArrivo(@PathVariable Long id, Model model) {
		Optional<Arrivi> oparrivo = arriviRepository.findById(id);
		if (oparrivo.isEmpty())
			return "";
		Arrivi arrivo = oparrivo.get();
		
		model.addAttribute("binsDisponibili",binRepository.findAll());

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
	@PostMapping("/save")
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
		
		arriviService.calcolaFrigo(arrivo);
		// arriviService.eseguiCalcoli(arrivo);

		Configurazione conf = configurazioneService.getConfigurazione();
		arrivo.setKey(conf.getInstallationId());

		arriviRepository.save(arrivo);

		movimentiBinService.register(arrivo);

		redirectAttributes.addFlashAttribute("msg", "Arrivo salvato correttamente");

		return "redirect:/web/arrivi/new";
	}

	@GetMapping("/new")
	public String newArrivo(Model model) {

		Arrivi arrivo = new Arrivi();
		arrivo.setData(LocalDate.now());
		arrivo.setBins(new ArrayList<>());

		model.addAttribute("arrivo", arrivo);
		model.addAttribute("listamerce", merceRepository.findAll());

		// 🔥 FIX IMPORTANTE: DTO NON ENTITY
		model.addAttribute("binsList", binMapper.toDtoList(binRepository.findAll()));
		model.addAttribute("configurazione", configurazioneService.getConfigurazione());

		return "new_arrivo";
	}

}