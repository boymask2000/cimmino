package com.cimmino.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cimmino.shop.database.CommercianteRepository;
import com.cimmino.shop.database.VenditeRepository;
import com.cimmino.shop.service.NETService;


@Controller
@RequestMapping("/net")
public class NETController {
	@Autowired
	CommercianteRepository commercianteRepository;
	@Autowired
	VenditeRepository venditeRepository;
	


	@Autowired
	NETService netService;


}
