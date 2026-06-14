package com.cimmino.shop.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gruppovendite")
public class GruppoVenditeRestController {

	@GetMapping("/checkAvailabilityOnBin/{numBin}/{arriviSelect}")
	public Map<String, Boolean> checkAvailabilityOnBin(@PathVariable int numBin, //
			@PathVariable String arriviSelect, //
			Model model) {

		String vals[] = arriviSelect.split(",");
		int avail;

		if (vals.length < 4)
			avail = -1;
		else
			avail = Integer.parseInt(vals[4]);
		
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		if( avail<0)
			map.put("check", false);
		else map.put("check", true);
		
		map.put("available", numBin<=avail);

		//return Map.of("available", numBin<=avail);
return map;
	}
}