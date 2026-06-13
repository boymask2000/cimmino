package com.cimmino.shop.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	public List<MagazzinoRow>  dump() {
		Map<String, MagazzinoRow> map = new HashMap<String, MagazzinoRow>();

		List<Arrivi> arrivi = arriviRepository.findAll();

		for (Arrivi arr : arrivi) {
			List<BinsArrivi> binsArr = arr.getBins();
			for (BinsArrivi barr : binsArr) {
				String key = arr.getData().toString() + " " + barr.getBin().getName()+" "+
				barr.getArrivo().getMerce().getName();
				
				MagazzinoRow row = null;

				if (map.get(key) == null) {
					row = new MagazzinoRow();
					row.setDate(arr.getData());
					row.setBin(barr.getBin().getName());
					row.setNum(barr.getNumBins());
					row.setNomeMerce(barr.getArrivo().getMerce().getName());
					
					row.setMerceId(barr.getArrivo().getMerce().getMerce_id());
					row.setBinId(barr.getBin().getId());
					row.setArrivoId(arr.getId());
					
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
				String key = ven.getArrivo().getData().toString() + " " + bven.getBin().getName()+" "+
				ven.getArrivo().getMerce().getName();
				MagazzinoRow row = null;
				if (map.get(key) == null) {
					row = new MagazzinoRow();
					row.setDate(ven.getData());
					row.setBin(bven.getBin().getName());
					row.setNum(-bven.getNumBins());
					row.setNomeMerce(ven.getArrivo().getMerce().getName());
					row.setMerceId(ven.getArrivo().getMerce().getMerce_id());
					row.setBinId(bven.getBin().getId());
					row.setArrivoId(ven.getArrivo().getId());
					map.put(key, row);
					continue;
				}
				row = map.get(key);

				row.setNum(row.getNum() - bven.getNumBins());
				map.put(key, row);
			}
		}

		
		
		List<MagazzinoRow> rows =
			   map
			        .values()
			        .stream()
			        .sorted(Comparator.comparing(r -> r.getDate()))
			        .collect(Collectors.toList());
		
		return rows;

	}
}
