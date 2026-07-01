package com.cimmino.shop.beans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;

import jakarta.annotation.PostConstruct;

@Component
public class BinsPool {
	@Autowired
	BinRepository binRepository;

	private List<Bin> lista;

	@PostConstruct
	public void init() {
		lista = binRepository.findAll();

		for (Bin bin : lista)
			System.out.println(bin.getName());
	}

	public List<Bin> getBins() {
		
		return List.copyOf(lista);
	}
}
