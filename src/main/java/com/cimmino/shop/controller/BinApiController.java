package com.cimmino.shop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;

@RestController
@RequestMapping("/api/bin")
public class BinApiController {

	@Autowired
	private BinRepository binRepository;

	@GetMapping("/{id}/peso")
	public Integer getPeso(@PathVariable Long id) {
		Optional<Bin> opbin = binRepository.findById(id);
		if (opbin.isPresent())
			return opbin.get().getPeso_lordo() - opbin.get().getTara();
		return 0;

//        Integer vv = binRepository.findById(id)
//                .map(Bin::getPeso_lordo)
//                .orElse((int) 0.0);
//        
//        return vv;
	}
}