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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsGruppoVenditeRepository;
import com.cimmino.shop.database.BinsVenditeRepository;
import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.GruppoVendite;
import com.cimmino.shop.database.GruppoVenditeRepository;
import com.cimmino.shop.database.MerceRepository;
import com.cimmino.shop.database.VenditeRepository;
import com.cimmino.shop.service.CommonService;
import com.cimmino.shop.service.ConfigurazioneService;
import com.cimmino.shop.service.GruppoVenditeService;
import com.cimmino.shop.service.MagazzinoRow;
import com.cimmino.shop.service.MagazzinoService;
import com.cimmino.shop.service.VenditeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/gruppovendite")
public class GruppoVenditeController {
	@Autowired
	CommonService commonService;
	@Autowired
	GruppoVenditeService gruppoVenditeService;
	@Autowired
	MagazzinoService magazzinoService;
	@Autowired
	ConfigurazioneService configurazioneService;
	@Autowired
	VenditeRepository venditeRepository;
	@Autowired
	ArriviRepository arriviRepository;
	@Autowired
	MerceRepository merceRepository;
	@Autowired
	GruppoVenditeRepository gruppoVenditeRepository;
	@Autowired
	BinsVenditeRepository binsVenditeRepository;
	@Autowired
	CommercianteRepository commercianteRepository;
	@Autowired
	BinRepository binRepository;
	@Autowired
	BinsGruppoVenditeRepository binsGruppoVenditeRepository;
	@Autowired
	VenditeService venditeService;

	@GetMapping("/new")
	public String newGruppo(Model model) {

		GruppoVendite gruppo = new GruppoVendite();

		List<Map<String, Object>> binsJs = binRepository.findAll().stream().map(b -> {
			Map<String, Object> m = new HashMap<>();
			m.put("id", b.getId());
			m.put("name", b.getName());
			m.put("pesoLordo", b.getPesoLordo());
			m.put("tara", b.getTara());
			return m;
		}).toList();

//		List<Map<String, Object>> arriviJs = binRepository.findAll().stream().map(b -> {
//			Map<String, Object> m = new HashMap<>();
//			m.put("id", 0);
//			m.put("name", "pp");
//			m.put("pesoLordo", 0);
//			m.put("tara", 0);
//			return m;
//		}).toList();
		List<Map<String, Object>> arriviJs = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> merceJs = merceRepository.findAll().stream().map(b -> {
			Map<String, Object> m = new HashMap<>();
			m.put("id", b.getMerce_id());
			m.put("name", b.getName());

			return m;
		}).toList();

		List<MagazzinoRow> magazzinorows = magazzinoService.dump();

		model.addAttribute("magazzinorows", magazzinorows);

		gruppo.setData(LocalDate.now());
		model.addAttribute("currData", LocalDate.now());
		model.addAttribute("commercianti", commercianteRepository.findAll());
		model.addAttribute("configurazione", configurazioneService.getConfigurazione());
		model.addAttribute("gruppo", gruppo);
		model.addAttribute("binsJs", binsJs);
		model.addAttribute("arriviJs", arriviJs);
		model.addAttribute("merceJs", merceJs);
		return "new_gruppo";
	}

	@GetMapping("/getArrivi")
	@ResponseBody
	public List<String> getMagazzino(@RequestParam Long merceId, @RequestParam Long binId) {

		List<String> out = new ArrayList<String>();

		List<MagazzinoRow> rows = magazzinoService.dump();

		rows = rows.stream().filter(p -> p.getNum() > 0).toList();

		if (merceId != 0) {
			rows = rows.stream().filter(p -> p.getMerceId() == merceId).toList();
		}

		if (binId != 0) {
			rows = rows.stream().filter(p -> p.getBinId() == binId).toList();
		}
		for (MagazzinoRow row : rows) {

			String v = row.getArrivoId() + "," + row.getDate() + "," + row.getNomeMerce() + "," + row.getBin() + ","
					+ row.getNum();
			out.add(v);

		}

		return out;
	}

	@GetMapping("/show/{id}")
	public String showGruppo(@PathVariable Long id, Model model) {
		Optional<GruppoVendite> opven = gruppoVenditeRepository.findById(id);
		if (opven.isEmpty())
			return "";
		GruppoVendite gruppo = opven.get();
		
//		int num =gruppo.getBins().stream().mapToInt(b -> b.getNumBins()).sum();
//
//		gruppo.setNumeroTotaleBins(num);
		model.addAttribute("gruppo", gruppo);

		return "handle_gruppo";
	}

	@PostMapping("/save")
	public String save( //
			@ModelAttribute GruppoVendite gr, //
			@RequestParam String binsJson, //
			@RequestParam LocalDate currData, //
			HttpSession session,  //
			Model model) {
		
		gruppoVenditeService.saveGruppoVendite(gr,currData, binsJson);
		
		commonService.setGoArrivi(session, model);

		return "home";
	}
	

}