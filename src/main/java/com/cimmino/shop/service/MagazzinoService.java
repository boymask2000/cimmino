package com.cimmino.shop.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.BinsVendite;
import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.VenditeRepository;

@Service
public class MagazzinoService {
	@Autowired
	private ArriviRepository arriviRepository;
	@Autowired
	VenditeRepository venditeRepository;

	public Map<String, MagazzinoRow> dump() {
		Map<String, MagazzinoRow> map = new HashMap<String, MagazzinoRow>();

		List<Arrivi> arrivi = arriviRepository.findAll();

		for (Arrivi arr : arrivi) {
			List<BinsArrivi> binsArr = arr.getBins();
			for (BinsArrivi barr : binsArr) {
				String key = arr.getData().toString() + " " + barr.getBin().getName();

				MagazzinoRow row = null;

				if (map.get(key) == null) {
					row = new MagazzinoRow();
					row.setDate(arr.getData());
					row.setBin(barr.getBin().getName());
					row.setNum(barr.getNumBins());

					map.put(key, row);
					continue;
				}
				row = map.get(key);
				row.setNum(row.getNum() + barr.getNumBins());
				map.put(key, row);
			}
		}

		List<Vendita> vendite = venditeRepository.findAll();

		for (Vendita ven : vendite) {
			List<BinsVendite> binsVen = ven.getBins();
			for (BinsVendite bven : binsVen) {
				String key = ven.getData().toString() + " " + bven.getBin().getName();
				
				MagazzinoRow row = null;
				if (map.get(key) == null) {
					row = new MagazzinoRow();
					row.setDate(ven.getData());
					row.setBin(bven.getBin().getName());
					row.setNum(-bven.getNumBins());

					map.put(key, row);
					continue;
				}
				row = map.get(key);

				row.setNum(row.getNum() - bven.getNumBins());
				map.put(key, row);
			}
		}

		return map;

	}
}
