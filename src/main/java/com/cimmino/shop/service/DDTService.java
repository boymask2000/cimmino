package com.cimmino.shop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.DDT;
import com.cimmino.shop.database.DDTRepository;
import com.cimmino.shop.database.dto.DDTDTO;
import com.cimmino.shop.mappers.DDTMapper;

@Service
public class DDTService {
	@Autowired
	DDTRepository ddtRepository;
	@Autowired
	DDTMapper ddtMapper;

	public DDTDTO create(String body) {
		DDT ddt = new DDT();

		ddt.setBody(body);

		DDT d = ddtRepository.save(ddt);

		return ddtMapper.toDto(d);

	}

	public DDTDTO getDDT(Long id) {
		Optional<DDT> op = ddtRepository.findById(id);
		DDT ddt =  op.get();
		return ddtMapper.toDto(ddt);
	}
}
