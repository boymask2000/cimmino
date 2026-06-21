package com.cimmino.shop.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimmino.shop.database.DDTRepository;

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
