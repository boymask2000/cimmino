package com.cimmino.shop.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cimmino.shop.CommerciantiDDTInfo;
import com.cimmino.shop.database.Commerciante;
import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.DDTRepository;
import com.cimmino.shop.database.GruppoVendite;
import com.cimmino.shop.database.GruppoVenditeRepository;
import com.cimmino.shop.database.TitolareRepository;
import com.cimmino.shop.database.TrasportatoreRepository;
import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.VenditeRepository;
import com.cimmino.shop.service.print.DDTInputData;

@Controller
@RequestMapping("/ddt")
public class DDTRestController {
	@Autowired
	DDTRepository ddtRepository;


	@GetMapping("/check")
	@ResponseBody
	public Map<String, Object> checkDDT(
	        @RequestParam String numeroDDT) {

	    boolean exists = ddtRepository.existsByNumeroDDT(numeroDDT);

	    return Map.of("exists", exists);
	}
}
