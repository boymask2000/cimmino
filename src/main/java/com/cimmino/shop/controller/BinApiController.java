package com.cimmino.shop.controller;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;
import com.cimmino.shop.database.BinsArriviRepository;
import com.cimmino.shop.database.VenditeRepository;
import com.cimmino.shop.service.BinArriviService;

@RestController
@RequestMapping("/api/bin")
public class BinApiController {
	@Autowired
	BinArriviService binArriviService;
	@Autowired
	private BinRepository binRepository;

	@Autowired
	BinsArriviRepository binsArriviRepository;

	@Autowired
	VenditeRepository venditeRepository;

	@GetMapping("/{id}/peso")
	public BigDecimal getPeso(@PathVariable Long id) {
		Optional<Bin> opbin = binRepository.findById(id);
		if (opbin.isPresent())
			return opbin.get().getPesoLordo().subtract(opbin.get().getTara());
		return BigDecimal.ZERO;

//        Integer vv = binRepository.findById(id)
//                .map(Bin::getPeso_lordo)
//                .orElse((int) 0.0);
//        
//        return vv;
	}

	@GetMapping("/{binid}/{arrivoId}/availability")
	public Map<String, Integer> availability(@PathVariable Long binid, @PathVariable Long arrivoId) {

		int avail = binArriviService.calcAvail(binid, arrivoId);

//		int total = binsArriviRepository.findByBin_Id(binid).map(BinsArrivi::getNumBins).orElse(0);
//
//		System.out.println("BIN=" + binid + " TOTAL=" + total + " SOLD=" + sold);

		return Map.of("available", avail);
	}

	
}