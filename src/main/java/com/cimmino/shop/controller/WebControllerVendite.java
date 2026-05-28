package com.cimmino.shop.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsVendite;
import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.Vendite;
import com.cimmino.shop.database.VenditeRepository;
import com.cimmino.shop.database.dto.BinsVenditaDTO;
import com.cimmino.shop.database.dto.VenditaDTO;
import com.cimmino.shop.mappers.BinsVenditaMapper;
import com.cimmino.shop.service.ConfigurazioneService;
import com.cimmino.shop.service.VenditeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/web")
public class WebControllerVendite {
	@Autowired
	ArriviRepository arriviRepository;
	@Autowired
	CommercianteRepository commercianteRepository;
	@Autowired
	BinRepository binRepository;
	@Autowired
	VenditeService venditeService;
	@Autowired
	ConfigurazioneService configurazioneService;
	@Autowired
	private VenditeRepository venditeRepository;

	@Autowired
	private BinsVenditaMapper binsVenditaMapper;

	@PostMapping("/vendita/save1")
	public String save(@ModelAttribute VenditaDTO dto) {
		venditeService.create(dto);
		return "redirect:/web/vendite";
	}

	@GetMapping("/vendita/new/{id}")
	public String newVendita(@PathVariable Long id, Model model) {
		Vendite vendita = new Vendite();
		vendita.setData(LocalDate.now()); // 👈 data corrente

		Optional<Arrivi> arr = arriviRepository.findById(id);
		vendita.setArrivo(arr.get());

		model.addAttribute("arrivo", arr.get());
		model.addAttribute("vendita", vendita);
		// model.addAttribute("bins", arr.get().getBins());
		model.addAttribute("commercianti", commercianteRepository.findAll());
		model.addAttribute("currData", LocalDate.now());

		List<Map<String, Object>> binsJs = arr.get().getBins().stream().map(b -> {
			Map<String, Object> m = new HashMap<>();
			m.put("id", b.getBin().getId());
			m.put("name", b.getBin().getName());
			m.put("pesoLordo", b.getBin().getPesoLordo());
			m.put("tara", b.getBin().getTara());
			return m;
		}).toList();

		model.addAttribute("binsJs", binsJs);
		model.addAttribute("configurazione", configurazioneService.getConfigurazione());

		return "new_vendita";
	}

	@PostMapping("/vendita/save")
	public String saveVendita( //
			@ModelAttribute("vendita") Vendite vendita, //
			@RequestParam("commercianteId") Long commercianteId, @RequestParam String binsJson,
			@RequestParam("arrivoId") Long arrivoId, @RequestParam("currData") LocalDate currData, Model model) {

		if(vendita.getDdt().equals(""))
			vendita.setDdt(null);
		
		ObjectMapper mapper = new ObjectMapper();
		List<BinsVenditaDTO> bins = new ArrayList<BinsVenditaDTO>();
		try {
			bins = mapper.readValue(binsJson, new TypeReference<List<BinsVenditaDTO>>() {
			});

		} catch (Exception e) {

			e.printStackTrace();
		}

		// binRepository.findById(bins.)

		List<BinsVendite> entities = binsVenditaMapper.toEntityList(bins);
		for (BinsVendite b : entities) {
			b.setVendita(vendita);

			Optional<Bin> opbin = binRepository.findById(b.getBin().getId());
			if (opbin.isPresent()) {
				b.setBin(opbin.get());
			}
		}

		vendita.setBins(entities);

		Optional<Arrivi> oparr = arriviRepository.findById(arrivoId);
		Arrivi arr = oparr.get();
		vendita.setArrivo(arr);

		vendita.setData(currData);
		venditeService.save(vendita, commercianteId);

		LocalDate today = LocalDate.now();
		LocalDate firstDay = today.withDayOfMonth(1);
		LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

		model.addAttribute("startDate", firstDay);
		model.addAttribute("endDate", lastDay);
		return "home";
	}

	@GetMapping("/vendita/show/{id}")
	public String showVendita(@PathVariable Long id, Model model) {
		Optional<Vendite> vend = venditeRepository.findById(id);
		if (vend.isEmpty())
			return "show_vendita";
		Vendite v = vend.get();
		model.addAttribute("vendita", v);
		for (BinsVendite b : v.getBins()) {

			Optional<Bin> opbin = binRepository.findById(b.getBin().getId());
			if (opbin.isPresent()) {
				b.setBin(opbin.get());
			}
		}
		return "show_vendita";
	}

}