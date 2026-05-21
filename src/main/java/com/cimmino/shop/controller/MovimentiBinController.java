package com.cimmino.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cimmino.shop.database.BinMovimentoView;
import com.cimmino.shop.database.MovimentiBinRepository;
import com.cimmino.shop.database.MovimentoBin;

@Controller
@RequestMapping("/movimentibin")
public class MovimentiBinController {
	@Autowired
	MovimentiBinRepository movimentiBinRepository;

	@GetMapping("/lista")
	public String listaCommercianti(RedirectAttributes redirectAttributes) {

		return "redirect:/movimentibin/view";
	}

	@GetMapping("/view")
	public String view(Model model) {

		List<MovimentoBin> ll;

		ll = movimentiBinRepository.findAll();
		
		List<BinMovimentoView> riepilogo = movimentiBinRepository.getRiepilogoMovimenti();

		model.addAttribute("riepilogo", riepilogo);
		model.addAttribute("operazioni", ll);

		return "movimentibin";
	}

}
