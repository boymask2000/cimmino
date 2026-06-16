package com.cimmino.shop.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.GruppoVendite;
import com.cimmino.shop.database.GruppoVenditeRepository;
import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.VenditeRepository;

import jakarta.transaction.Transactional;

@Service
public class CommerciantiService {
	@Autowired
	VenditeRepository venditeRepository;
	@Autowired
	GruppoVenditeRepository gruppoVenditeRepository;

	@Transactional
	public Vendita updateFieldVendite(Long id, String field, String value) {

		Vendita op = venditeRepository.findById(id).orElseThrow();

		BigDecimal v = new BigDecimal(value);

		switch (field) {
		case "peso_lordo":
			op.setPeso_lordo(v);
			break;

		case "tara":
			op.setTara(v);
			break;

		case "scarto":
			op.setScarto(v);
			break;

		case "prezzo":
			op.setPrezzo(v);
			break;
		case "nettoDiScarto":
			op.setNettoDiScarto(v);
			break;
		}
		BigDecimal scarto = new BigDecimal(1);
		if (op.getScarto() != null) {
			BigDecimal perc = op.getScarto().divide(new BigDecimal(100));
			scarto = scarto.subtract(perc);
		}

		BigDecimal nettoDiScarto = op.getPeso_lordo().multiply(scarto);
		op.setNettoDiScarto(nettoDiScarto);
		BigDecimal importo = op.getNettoDiScarto().multiply(op.getPrezzo());
		op.setImporto(importo);

		venditeRepository.save(op);

		return op;
	}
	@Transactional
	public GruppoVendite updateFieldGroups(Long id, String field, String value) {

		GruppoVendite op = gruppoVenditeRepository.findById(id).orElseThrow();

		BigDecimal v = new BigDecimal(value);

		switch (field) {
		case "peso_lordo":
			op.setPeso_lordo(v);
			break;

		case "tara":
			op.setTara(v);
			break;

		case "scarto":
			op.setScarto(v);
			break;

		case "prezzo":
			op.setPrezzo(v);
			break;
		case "nettoDiScarto":
			op.setNettoDiScarto(v);
			break;
		}
		BigDecimal scarto = new BigDecimal(1);
		if (op.getScarto() != null) {
			BigDecimal perc = op.getScarto().divide(new BigDecimal(100));
			scarto = scarto.subtract(perc);
		}

		BigDecimal nettoDiScarto = op.getPeso_lordo().multiply(scarto);
		op.setNettoDiScarto(nettoDiScarto);
		BigDecimal importo = op.getNettoDiScarto().multiply(op.getPrezzo());
		op.setImporto(importo);

		gruppoVenditeRepository.save(op);

		return op;
	}
}
