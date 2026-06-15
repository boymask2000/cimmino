package com.cimmino.shop.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.GruppoVendite;
import com.cimmino.shop.database.GruppoVenditeRepository;
import com.cimmino.shop.database.Vendita;

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

		List<Arrivi> arrivi = arriviRepository.cerca(startDate, endDate);
		
		List<GruppoVendite> gruppoVendite = gruppoVenditeRepository.findAll();

		arriviService.calcSums(arrivi);

		model.addAttribute("results", arrivi);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("gruppoVendite", gruppoVendite);
		
		for( GruppoVendite g: gruppoVendite) {
		
			int num =g.getBins().stream().mapToInt(b -> b.getNumBins()).sum();
			g.setNumeroTotaleBins(num);
		}
		
		for( Arrivi arr: arrivi) {
			for( Vendita ven: arr.getVendite()) {
				int num =ven.getBins().stream().mapToInt(b -> b.getNumBins()).sum();
				ven.setNumeroTotaleBins(num);
			}
		}
		
	}
}
