package com.cimmino.shop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cimmino.shop.database.Merce;
import com.cimmino.shop.database.MerceRepository;
import com.cimmino.shop.database.dto.MerceDTO;
import com.cimmino.shop.mappers.MerceMapper;
import com.cimmino.shop.service.AnalisiResultBean;
import com.cimmino.shop.service.AnalisiService;

@Controller
@RequestMapping("/web/analisi")
public class AnalisiController {

	@Autowired
	AnalisiService analisiService;
	@Autowired
	MerceRepository merceRepository;
	@Autowired
	MerceMapper merceMapper;

	@GetMapping("/show/{id}")
	public String newAnalisi(@PathVariable Long id, Model model) {
		AnalisiResultBean result = analisiService.analize(id);
		
		
		Long merceId = result.getArrivo().getMerceId();
		Optional<Merce> merceop = merceRepository.findById(merceId);
		Merce merce=null;
		if(merceop.isPresent())
			merce = merceop.get();
		
		MerceDTO merceDto = merceMapper.toDto(merce);
		
		model.addAttribute("merceDto", merceDto);
		model.addAttribute("analisi", result);
		return "analisi";
	}
}