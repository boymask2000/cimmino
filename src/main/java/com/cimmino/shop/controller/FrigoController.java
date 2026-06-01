package com.cimmino.shop.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.BinMovimentoView;
import com.cimmino.shop.database.dto.ArriviDTO;
import com.cimmino.shop.service.ArriviService;

@Controller
@RequestMapping("/frigo")
public class FrigoController {
	@Autowired
	ArriviService arriviService;
	@Autowired
	ArriviRepository arriviRepository;

	@GetMapping("/show")
	public String show(Model model) {
		List<ArriviDTO> rows = arriviService.getAll() //
				.stream()//
				.sorted(Comparator.comparing(r -> r.getData()))//
				.collect(Collectors.toList());

		model.addAttribute("rows", rows);

		LocalDate today = LocalDate.now();
		LocalDate firstDay = today.withDayOfMonth(1);
		LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

		model.addAttribute("startDate", firstDay);
		model.addAttribute("endDate", lastDay);

		return "frigo";
	}

	@GetMapping("/searchResult")
	public String view(@RequestParam(required = false) LocalDate dataDa,
			@RequestParam(required = false) LocalDate dataA,

			Model model) {

		List<Arrivi> arrivi = arriviRepository.cerca(dataDa, dataA);
		
		BigDecimal totaleFrigoxCaldo = arrivi.stream()
			    .filter(a -> Boolean.TRUE.equals(a.getPagoFrigo()))
			    .map(Arrivi::getFrigoxCaldo)
			    .filter(Objects::nonNull)
			    .reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal totaleFrigoxFreddo = arrivi.stream()
			    .filter(a -> Boolean.TRUE.equals(a.getPagoFrigo()))
			    .map(Arrivi::getFrigoxFreddo)
			    .filter(Objects::nonNull)
			    .reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal sum = totaleFrigoxFreddo.add(totaleFrigoxCaldo);

		model.addAttribute("arrivi", arrivi);


		model.addAttribute("totaleFrigoxCaldo", totaleFrigoxCaldo);
		model.addAttribute("totaleFrigoxFreddo", totaleFrigoxFreddo);
		model.addAttribute("sum", sum);

		model.addAttribute("startDate", dataDa);
		model.addAttribute("endDate", dataA);

		return "frigo";
	}
}
