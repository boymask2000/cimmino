package com.cimmino.shop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.DDT;
import com.cimmino.shop.database.DDTRepository;
import com.cimmino.shop.database.dto.DDTDTO;
import com.cimmino.shop.mappers.DDTMapper;
import com.cimmino.shop.service.print.DDTInputData;

@Service
public class DDTService {
	@Autowired
	DDTRepository ddtRepository;
	@Autowired
	DDTMapper ddtMapper;

	public DDTDTO create(String body, DDTInputData ddtInputData) {
		DDT ddt = new DDT();
		ddt.setNumeroDDT(ddtInputData.getNumeroDDT());
		ddt.setBody(body);

		DDT d = ddtRepository.save(ddt);

		return ddtMapper.toDto(d);

	}

	public DDTDTO getDDT(String dttnum) {
		Optional<DDT> op = ddtRepository.findByNumDDT(dttnum);
		if( op.isEmpty()) {
			DDT ddt = new DDT();
			return ddtMapper.toDto(ddt);
		}
		DDT ddt = op.get();
		return ddtMapper.toDto(ddt);
	}

	public DDTDTO create(String body, DDTDTO dto1) {
		dto1.setBody(body);
		DDT entity = ddtMapper.toEntity(dto1);
		DDT d = ddtRepository.save(entity);
		return ddtMapper.toDto(d);
	}
}
