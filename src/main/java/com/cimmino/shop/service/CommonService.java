package com.cimmino.shop.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.GruppoVendite;
import com.cimmino.shop.database.GruppoVenditeRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CommonService {
	@Autowired
	ArriviRepository arriviRepository;
	@Autowired
	ArriviService arriviService;
	@Autowired
	GruppoVenditeRepository gruppoVenditeRepository;

	public void setGoArrivi(HttpSession session, Model model) {
		LocalDate startDate = (LocalDate) session.getAttribute("startDate");
		LocalDate endDate = (LocalDate) session.getAttribute("endDate");

		List<Arrivi> risultati = arriviRepository.cerca(startDate, endDate);
		
		List<GruppoVendite> gruppoVendite = gruppoVenditeRepository.findAll();

		arriviService.calcSums(risultati);

		model.addAttribute("results", risultati);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("gruppoVendite", gruppoVendite);
		arriviService.calcNumTotaleBins(risultati);
	}
}
