package com.cimmino.shop.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimmino.shop.service.NETService;

@RestController
@RequestMapping("/net")
public class NETRestController {

	@Autowired
	NETService netService;

	@GetMapping("/debug")
	public Map<String, Object> debug() throws Exception {
		return netService.runDiagnostics();
	}
}
