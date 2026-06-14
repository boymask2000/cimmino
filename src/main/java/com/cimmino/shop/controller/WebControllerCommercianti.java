package com.cimmino.shop.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.VenditeRepository;

@Controller
@RequestMapping("/web")
public class WebControllerCommercianti {

	@Autowired
	CommercianteRepository commercianteRepository;
	@Autowired
	VenditeRepository venditeRepository;

	@GetMapping("/commercianti/filter2")
	public String filter2(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate, Model model) {

		List<Vendita> risultati = venditeRepository.cerca(startDate, endDate);

		model.addAttribute("commercianti", commercianteRepository.findAll());
		model.addAttribute("operazioni", risultati);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return "operazioni_commercianti";
	}

	@GetMapping("/commercianti/lista2/{id}")
	public String listaCommercianti2(@RequestParam(required = false, defaultValue = "0") Long commercianteId,
			RedirectAttributes redirectAttributes) {

		redirectAttributes.addAttribute("commercianteId", commercianteId);

		if (commercianteId == 0) {
			redirectAttributes.addFlashAttribute("msg", "Nessun filtro applicato");
		} else {
			redirectAttributes.addFlashAttribute("msg", "Filtri applicati");
		}

		return "redirect:/web/commercianti/view2";
	}

	@GetMapping("/commercianti/view2")
	public String view2(@RequestParam(required = false, defaultValue = "0") Long commercianteId, Model model) {

		List<Vendita> ll;

		if (commercianteId == 0) {
			ll = venditeRepository.findAll();
		} else {
			ll = venditeRepository.findByCommerciante(commercianteId);
		}

		BigDecimal totale = ll.stream().map(Vendita::getImporto).filter(Objects::nonNull).reduce(BigDecimal.ZERO,
				BigDecimal::add)
		// .setScale(2, RoundingMode.HALF_UP)
		;

		model.addAttribute("operazioni", ll);
		model.addAttribute("commercianti", commercianteRepository.findAll());
		model.addAttribute("commercianteId", commercianteId);
		model.addAttribute("totale", totale);
		return "operazioni_commercianti";
	}

	@PostMapping("/commercianti/update-field")
	@ResponseBody
	public ResponseEntity<?> updateField(@RequestBody UpdateFieldRequest req) {

		Vendita op = venditeRepository.findById(req.getId()).orElseThrow();

		switch (req.getField()) {

		case "ddt":
			op.setDdt(req.getValue());
			break;
		case "tara":
			op.setTara(new BigDecimal(req.getValue()));
			break;
		case "scarto":
			op.setScarto(new BigDecimal(req.getValue()));
			break;

		case "prezzo":
			op.setPrezzo(new BigDecimal(req.getValue()));
			break;

		case "peso_lordo":
			op.setPeso_lordo(new BigDecimal(req.getValue()));
			break;
		case "nettoDiScarto":
			op.setNettoDiScarto(new BigDecimal(req.getValue()));
			break;
		case "nettoDiTara":
			op.setNettoDiTara(new BigDecimal(req.getValue()));
			break;
		}
		
		BigDecimal importo = op.getNettoDiScarto().multiply(op.getPrezzo());
		op.setImporto(importo);
		

		venditeRepository.save(op);

		return ResponseEntity.ok().body(Map.of("status", "ok"));
	}
}