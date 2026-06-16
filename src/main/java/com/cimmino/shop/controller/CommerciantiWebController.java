package com.cimmino.shop.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.GruppoVendite;
import com.cimmino.shop.database.GruppoVenditeRepository;
import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.VenditeRepository;
import com.cimmino.shop.service.CommerciantiService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/web")
public class CommerciantiWebController {
	@Autowired
	CommerciantiService commerciantiService;
	@Autowired
	CommercianteRepository commercianteRepository;
	@Autowired
	VenditeRepository venditeRepository;
	@Autowired
	GruppoVenditeRepository gruppoVenditeRepository;

	@GetMapping("/commercianti/filter/groups")
	public String filter_groups(@RequestParam LocalDate startDate, //
			@RequestParam LocalDate endDate, //
			HttpSession session, //
			Model model) {

		if (startDate != null) {
			session.setAttribute("startDate", startDate);
		}
		if (endDate != null) {
			session.setAttribute("endDate", endDate);
		}
		List<GruppoVendite> risultati = gruppoVenditeRepository.cerca(startDate, endDate);

		model.addAttribute("commercianti", commercianteRepository.findAll());
		model.addAttribute("operazioni", risultati);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return "operazioni_commercianti_groups";
	}

	@GetMapping("/commercianti/filter/groups/commerciante/{id}")
	public String filter_groups_commercianti( //
			@RequestParam(required = false, defaultValue = "0") Long commercianteId, //
			RedirectAttributes redirectAttributes) {

		redirectAttributes.addAttribute("commercianteId", commercianteId);

		if (commercianteId == 0) {
			redirectAttributes.addFlashAttribute("msg", "Nessun filtro applicato");
		} else {
			redirectAttributes.addFlashAttribute("msg", "Filtri applicati");
		}

		return "redirect:/web/commercianti/groups/view2";
	}

	@GetMapping("/commercianti/groups/view2")
	public String groups_view2(@RequestParam(required = false, defaultValue = "0") Long commercianteId, Model model) {

		List<GruppoVendite> ll;

		if (commercianteId == 0) {
			ll = gruppoVenditeRepository.findAll();
		} else {
			ll = gruppoVenditeRepository.findVenditeDiCommerciante(commercianteId);
		}

		BigDecimal totale = ll.stream().map(GruppoVendite::getImporto).filter(Objects::nonNull).reduce(BigDecimal.ZERO,
				BigDecimal::add)
		// .setScale(2, RoundingMode.HALF_UP)
		;

		model.addAttribute("operazioni", ll);
		model.addAttribute("commercianti", commercianteRepository.findAll());
		model.addAttribute("commercianteId", commercianteId);
		model.addAttribute("totale", totale);
		return "operazioni_commercianti_groups";
	}

	@PostMapping("/commercianti/groups/update-field")
	@ResponseBody
	public Map<String, Object> updateField(@RequestBody UpdateFieldRequest req) {

		GruppoVendite op = commerciantiService.updateFieldGroups(req.getId(), req.getField(), req.getValue());

		Map<String, Object> res = new HashMap<>();

		res.put("importo", op.getImporto());
		res.put("nettoDiTara", op.getNettoDiTara());
		res.put("nettoDiScarto", op.getNettoDiScarto());
		res.put("scarto", op.getScarto());
		res.put("tara", op.getTara());

		return res;
	}
	@GetMapping("/commercianti/filter/vendite")
	public String filter_vendite(@RequestParam LocalDate startDate, //
			@RequestParam LocalDate endDate, //
			HttpSession session, //
			Model model) {

		if (startDate != null) {
			session.setAttribute("startDate", startDate);
		}
		if (endDate != null) {
			session.setAttribute("endDate", endDate);
		}
		List<Vendita> risultati = venditeRepository.cerca(startDate, endDate);

		model.addAttribute("commercianti", commercianteRepository.findAll());
		model.addAttribute("operazioni", risultati);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return "operazioni_commercianti_vendite";
	}
	@PostMapping("/commercianti/vendite/update-field")
	@ResponseBody
	public Map<String, Object> updateFieldVendite(@RequestBody UpdateFieldRequest req) {

		Vendita op = commerciantiService.updateFieldVendite(req.getId(), req.getField(), req.getValue());

		Map<String, Object> res = new HashMap<>();

		res.put("importo", op.getImporto());
		res.put("nettoDiTara", op.getNettoDiTara());
		res.put("nettoDiScarto", op.getNettoDiScarto());
		res.put("scarto", op.getScarto());
		res.put("tara", op.getTara());

		return res;
	}
	@GetMapping("/commercianti/filter/vendite/commerciante/{id}")
	public String filter_vendite_commercianti( //
			@RequestParam(required = false, defaultValue = "0") Long commercianteId, //
			RedirectAttributes redirectAttributes) {

		redirectAttributes.addAttribute("commercianteId", commercianteId);

		if (commercianteId == 0) {
			redirectAttributes.addFlashAttribute("msg", "Nessun filtro applicato");
		} else {
			redirectAttributes.addFlashAttribute("msg", "Filtri applicati");
		}

		return "redirect:/web/commercianti/vendite/view2";
	}
	@GetMapping("/commercianti/vendite/view2")
	public String gvendite_view2(@RequestParam(required = false, defaultValue = "0") Long commercianteId, Model model) {

		List<Vendita> ll;

		if (commercianteId == 0) {
			ll = venditeRepository.findAll();
		} else {
			ll = venditeRepository.findVenditeDiCommerciante(commercianteId);
		}

		BigDecimal totale = ll.stream().map(Vendita::getImporto).filter(Objects::nonNull).reduce(BigDecimal.ZERO,
				BigDecimal::add)
		// .setScale(2, RoundingMode.HALF_UP)
		;

		model.addAttribute("operazioni", ll);
		model.addAttribute("commercianti", commercianteRepository.findAll());
		model.addAttribute("commercianteId", commercianteId);
		model.addAttribute("totale", totale);
		return "operazioni_commercianti_vendite";
	}
}